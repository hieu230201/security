package com.example.securitybase.repository.systems.impl;

import com.example.securitybase.entity.SysGroupFull;
import com.example.securitybase.entity.SysUser;
import com.example.securitybase.repository.systems.SysGroupFullRepository;
import com.example.securitybase.repository.systems.SysRoleRepository;
import com.example.securitybase.repository.systems.SysUserRepositoryCustom;
import com.example.securitybase.util.SqlQueryUtil;
import com.example.securitybase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SysUserRepositoryImpl implements SysUserRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysGroupFullRepository sysGroupFullRepository;

    @Autowired
    private SqlQueryUtil sqlQueryUtil;

    @Override
    public Page<SysUser> findAllByPaging(String username, Pageable pageable) {
        HashMap<String, Object> params = new HashMap<>();

        var sql = "SELECT * FROM SYS_USER WHERE 1 = 1 ";

        if(StringUtil.isNotNullAndEmpty(username)){
            username = username.toLowerCase(Locale.ROOT);
            params.put("username", "%"+username+"%");
            sql += "AND LOWER(USERNAME) LIKE :username";
        }


        return sqlQueryUtil.queryEntity().querySqlPaging(sql, params, SysUser.class, pageable);
    }

    @Override
    public List<SysUser> findByGroupIdAndRoleId(Long groupId, Long roleId) {
        var sql = "SELECT A FROM SysUser A\n" +
                "JOIN SysGroupUser B ON A.id = B.userId\n" +
                "WHERE B.groupId = :groupId AND B.roleId = :roleId";
        Query query = entityManager.createQuery(sql, SysUser.class);
        query.setParameter("groupId", groupId);
        query.setParameter("roleId", roleId);

        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public List<SysUser> findByGroupId(Long groupId) {
        var sql = "SELECT A FROM SysUser A\n" +
                "JOIN SysGroupUser B ON A.id = B.userId\n" +
                "WHERE B.groupId = :groupId";
        Query query = entityManager.createQuery(sql, SysUser.class);
        query.setParameter("groupId", groupId);

        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public List<SysUser> findNotInGroupId(Long groupId) {
        var sql = "SELECT C FROM SysUser C WHERE\n" +
                "C.username NOT IN (\n" +
                "    SELECT A.username FROM SysUser A\n" +
                "    JOIN SysGroupUser B ON A.id = B.userId\n" +
                "    WHERE B.groupId = :groupId\n" +
                ")";
        Query query = entityManager.createQuery(sql, SysUser.class);
        query.setParameter("groupId", groupId);

        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public List<SysUser> findNotInGroupId(Long groupId, String username) {
        if(username == null || username.isBlank())
            return findNotInGroupId(groupId);

        var sql = "SELECT C FROM SysUser C WHERE\n" +
                "C.username NOT IN (\n" +
                "    SELECT A.username FROM SysUser A\n" +
                "    JOIN SysGroupUser B ON A.id = B.userId\n" +
                "    WHERE B.groupId = :groupId\n" +
                ") AND C.username LIKE :username";
        Query query = entityManager.createQuery(sql, SysUser.class);
        query.setParameter("groupId", groupId);
        query.setParameter("username", "%" + username + "%");

        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public Boolean isUserIdInGroupId(Long groupId, Long userId) {
        var sql = "SELECT A FROM SysUser A\n" +
                "JOIN SysGroupUser B ON A.id = B.userId\n" +
                "WHERE B.groupId = :groupId AND A.id = :userId";
        Query query = entityManager.createQuery(sql, SysUser.class);
        query.setParameter("groupId", groupId);
        query.setParameter("userId", userId);

        //noinspection unchecked
        var list = query.getResultList();
        return !list.isEmpty();
    }

    @Override
    public List<SysUser> findManages(Long groupId) {

        var params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        return sqlQueryUtil.queryEntity().querySql("SELECT A.* FROM SYS_USER A\n" +
                "JOIN SYS_GROUP_USER B ON A.ID = B.USER_ID\n" +
                "JOIN SYS_GROUP_FULL C ON B.GROUP_ID = C.ID\n" +
                "JOIN SYS_ROLE D ON D.ID = B.ROLE_ID\n" +
                "WHERE D.IS_MANAGE = 1 AND C.ID = :groupId",
                params,
                SysUser.class
                );
    }

    @Override
    public List<SysUser> findManagesHighLevel(Long groupId, Long userId, boolean forceJumpLevel) {

        var userRoles = sysRoleRepository.findByGroupIdAndUserId(groupId, userId);
        if(userRoles == null || userRoles.isEmpty()){
            return new ArrayList<>();
        }

        var firstRole = userRoles.get(0);
        if(!forceJumpLevel){
            if(firstRole.getManage() == null || !firstRole.getManage()){
                return findManages(groupId);
            }
        }


        var currentGroup = sysGroupFullRepository.findById(groupId).orElse(null);
        if(currentGroup == null || currentGroup.getParentId() == null || currentGroup.getParentId() == 0){
            return new ArrayList<>();
        }

        var parentGroup = sysGroupFullRepository.findById(currentGroup.getParentId()).orElse(null);
        if(parentGroup == null){
            return new ArrayList<>();
        }
        return findManages(parentGroup.getId());
    }

    public List<String> findAllUserByGroupKey(String groupKey) {
        var sql = "SELECT DISTINCT(su.USERNAME)\n" +
                "FROM SYS_GROUP_Full sgf\n" +
                "INNER JOIN SYS_GROUP_USER sgu ON sgu.Group_id = sgf.id\n" +
                "INNER JOIN SYS_User su ON su.id = sgu.user_id where sgf.KEY = :groupKey";
        var params = new HashMap<String, Object>();
        params.put("groupKey", groupKey);
        return sqlQueryUtil.queryModel().queryForList(sql,params, String.class);
    }

    public List<String> findAllUserInGroup(SysGroupFull sysGroup) {
        var sql = "SELECT DISTINCT(su.USERNAME)\n" +
                "FROM SYS_User su\n" +
                "WHERE su.id IN\n" +
                "    (SELECT sgu.USER_ID\n" +
                "     FROM SYS_GROUP_USER sgu\n" +
                "     WHERE GROUP_ID IN\n" +
                "         (SELECT sgf.id\n" +
                "          FROM SYS_GROUP_Full sgf\n" +
                "          WHERE sgf.LEFT > :left\n" +
                "            AND sgf.Right < :right))";
        var params = new HashMap<String, Object>();
        params.put("left", sysGroup.getLeft());
        params.put("right", sysGroup.getRight());
        return sqlQueryUtil.queryModel().queryForList(sql,params, String.class);
    }

    @Override
    public List<SysUser> findEmployeesByManagerId(Long userId){
        var params = new HashMap<String, Object>();
        params.put("userId", userId);

        String query = new StringBuilder()
                .append("SELECT su.* ")
                .append("FROM SYS_USER su ")
                .append("INNER JOIN SYS_GROUP_USER sgu ON sgu.user_id = su.id ")
                .append("INNER JOIN SYS_ROLE sr ON sr.id = sgu.role_id ")
                .append("WHERE sr.is_manage = 0 AND sgu.group_id IN (SELECT sgu.group_id FROM SYS_GROUP_USER sgu WHERE sgu.user_id = :userId)").toString();
        return sqlQueryUtil.queryEntity().querySql(query, params, SysUser.class);
    }

    @Override
    public boolean isLeader(Long userId){
        var params = new HashMap<String, Object>();
        params.put("userId", userId);

        String queryString = new StringBuilder()
                .append("SELECT COUNT(*) FROM SYS_GROUP_USER sgu ")
                .append("INNER JOIN SYS_ROLE sr ON sr.id = sgu.role_id ")
                .append("WHERE sr.is_manage = 1 AND sgu.user_id = :userId").toString();
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("userId", userId);
        var result = (BigDecimal) query.getSingleResult();
        return result.intValue() > 0;
    }

    @Override
    public List<SysUser> getListUserApprovedByRoleAndUserCV (Long userStaffId, String roleStaffId, String roleApprovedId) {
        String query = "SELECT listUserPD.* FROM SYS_USER listUserPD\n" +
                        "JOIN SYS_GROUP_USER groupRoleCV ON groupRoleCV.USER_ID = :userStaffId\n" +
                        "JOIN SYS_GROUP_USER groupRolePD ON groupRolePD.GROUP_ID = groupRoleCV.GROUP_ID\n" +
                        "WHERE groupRoleCV.ROLE_ID = :roleStaffId AND groupRolePD.ROLE_ID = :roleApprovedId\n" +
                        "AND listUserPD.ID = groupRolePD.USER_ID";
        var params = new HashMap<String, Object>();
        params.put("userStaffId", userStaffId);
        params.put("roleStaffId", roleStaffId);
        params.put("roleApprovedId", roleApprovedId);
        return sqlQueryUtil.queryModel().queryForList(query,params, SysUser.class);
    }

    @Override
    public List<SysUser> getListUserApprovedByListRoleAndUserCV (Long userStaffId, List<String> roleStaffKey, String roleApprovedKey) {
        String query = "SELECT DISTINCT a.USERNAME FROM SYS_USER A\n" +
                "JOIN SYS_GROUP_USER groupRoleKS ON groupRoleKS.USER_ID = A.ID\n" +
                "JOIN SYS_ROLE roleKS ON (roleKS.ID = groupRoleKS.ROLE_ID AND roleKS.KEY = :roleApprovedId)\n" +
                "JOIN SYS_GROUP_USER groupRoleCV ON (groupRoleKS.GROUP_ID = groupRoleCV.GROUP_ID )\n" +
                "JOIN SYS_ROLE roleCV ON (roleCV.ID = groupRoleCV.ROLE_ID AND groupRoleCV.USER_ID = :userStaffId AND roleCV.KEY IN (:roleStaffId))";
        var params = new HashMap<String, Object>();
        params.put("userStaffId", userStaffId);
        params.put("roleStaffId", roleStaffKey);
        params.put("roleApprovedId", roleApprovedKey);
        return sqlQueryUtil.queryModel().queryForList(query,params, SysUser.class);
    }

    @Override
    public List<SysUser> getListUserApproved (Long userStaffId, List<String> roleStaffKey, List<String> roleApprovedKey) {
        String query = "SELECT DISTINCT a.USERNAME FROM SYS_USER A\n" +
                "JOIN SYS_GROUP_USER groupRoleKS ON groupRoleKS.USER_ID = A.ID\n" +
                "JOIN SYS_ROLE roleKS ON (roleKS.ID = groupRoleKS.ROLE_ID AND roleKS.KEY IN (:roleApprovedId))\n" +
                "JOIN SYS_GROUP_USER groupRoleCV ON (groupRoleKS.GROUP_ID = groupRoleCV.GROUP_ID )\n" +
                "JOIN SYS_ROLE roleCV ON (roleCV.ID = groupRoleCV.ROLE_ID AND groupRoleCV.USER_ID = :userStaffId AND roleCV.KEY IN (:roleStaffId))";
        var params = new HashMap<String, Object>();
        params.put("userStaffId", userStaffId);
        params.put("roleStaffId", roleStaffKey);
        params.put("roleApprovedId", roleApprovedKey);
        return sqlQueryUtil.queryModel().queryForList(query,params, SysUser.class);
    }
}

