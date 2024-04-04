package com.example.securitybase.repository.systems.impl;

import com.example.securitybase.entity.SysGroupFull;
import com.example.securitybase.repository.systems.SysGroupFullRepositoryCustom;
import com.example.securitybase.util.SqlQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SysGroupFullRepositoryImpl implements SysGroupFullRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    SqlQueryUtil sqlQueryUtil;


//    @UseSafeRunning
//    @UseLogging
    @Override
    public List<SysGroupFull> getAllLeafNode(){
        String sql = "SELECT A FROM SysGroupFull A\n" +
                "WHERE A.right = A.left + 1";
        Query query = entityManager.createQuery(sql, SysGroupFull.class);

        return query.getResultList();
    }
    @Override
    public Boolean extendNode(SysGroupFull parentNode) {
        if(parentNode == null)
            return false;

        var sql = "UPDATE SysGroupFull A " +
                "SET A.right = A.right + 2 " +
                "WHERE A.right > :right";
        var query = entityManager.createQuery(sql);
        query.setParameter("right", parentNode.getRight());
        query.executeUpdate();

        sql = "UPDATE SysGroupFull A " +
                "SET A.left = A.left + 2 " +
                "WHERE A.left > :right";
        query = entityManager.createQuery(sql);
        query.setParameter("right", parentNode.getRight());
        query.executeUpdate();

        sql = "UPDATE SysGroupFull A " +
                "SET A.right = A.right + 2 " +
                "WHERE A.id = :parentId";
        query = entityManager.createQuery(sql);

        query.setParameter("parentId", parentNode.getId());
        query.executeUpdate();
        return true;
    }

    @Override
    public Boolean addNode(SysGroupFull node, SysGroupFull parentNode){
        if(node == null || parentNode == null)
            return false;
        if(!extendNode(parentNode)){
            return false;
        }
        var currentRight = parentNode.getRight();
        node.setLeft(currentRight);
        node.setRight(currentRight + 1);
        node.setParentId(parentNode.getId());
        entityManager.persist(node);
        return true;
    }

    @Override
    public Boolean removeNode(SysGroupFull node) {
        if(node == null)
            return false;

        var myLeft = node.getLeft();
        var myRight = node.getRight();
        var myWidth = myRight - myLeft + 1;

        var sql = "DELETE FROM SysGroupFull A \n" +
                "WHERE A.left BETWEEN :myLeft AND :myRight";
        var query = entityManager.createQuery(sql);
        query.setParameter("myLeft", myLeft);
        query.setParameter("myRight", myRight);
        query.executeUpdate();

        sql = "UPDATE SysGroupFull A " +
                "SET A.right = A.right - :myWidth " +
                "WHERE A.right > :myRight";

        query = entityManager.createQuery(sql);
        query.setParameter("myWidth", myWidth);
        query.setParameter("myRight", myRight);
        query.executeUpdate();

        sql = "UPDATE SysGroupFull A " +
                "SET A.left = A.left - :myWidth " +
                "WHERE A.left > :myRight";

        query = entityManager.createQuery(sql);
        query.setParameter("myWidth", myWidth);
        query.setParameter("myRight", myRight);
        query.executeUpdate();

        return true;
    }

    @Override
    public List<Long> getAllParentId(SysGroupFull node){
        return getAllParentNode(node).stream().map(SysGroupFull::getId).collect(Collectors.toList());
    }
    @Override
    public List<SysGroupFull> getAllParentNode(SysGroupFull node){
        if(node == null)
            return new ArrayList<>();

        String sql = "SELECT A FROM SysGroupFull A\n" +
                "WHERE A.left < :left AND A.right > :right";
        Query query = entityManager.createQuery(sql, SysGroupFull.class);
        query.setParameter("left", node.getLeft());
        query.setParameter("right", node.getRight());


        return query.getResultList();
    }

    @Override
    public List<SysGroupFull> getAllParentNodeByGroupType(SysGroupFull node, String groupTypeParent) {
        if(node == null)
            return new ArrayList<>();

        var sql = "SELECT A.* FROM SYS_GROUP_FULL A\n" +
                "JOIN SYS_GROUP_DETAIL B ON A.ID = B.GROUP_ID\n" +
                "WHERE A.LEFT < :left AND A.RIGHT > :right\n" +
                "AND B.GROUP_TYPE = :groupTypeParent ORDER BY A.ID";
        var params = new HashMap<String, Object>();
        params.put("left", node.getLeft());
        params.put("right", node.getRight());
        params.put("groupTypeParent", groupTypeParent);


        return sqlQueryUtil.queryEntity().querySql(sql, params, SysGroupFull.class);
    }

    @Override
    public List<SysGroupFull> getAllChildNode(SysGroupFull parentNode) {
        if(parentNode == null)
            return new ArrayList<>();

        String sql = "SELECT A FROM SysGroupFull A\n" +
                "WHERE A.left > :left AND A.right < :right";
        Query query = entityManager.createQuery(sql, SysGroupFull.class);
        query.setParameter("left", parentNode.getLeft());
        query.setParameter("right", parentNode.getRight());


        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysGroupFull> findByUserId(Long userId) {
        var sql = "SELECT DISTINCT C FROM SysUser A \n" +
                "INNER JOIN SysGroupUser B ON A.id = B.userId\n" +
                "INNER JOIN SysGroupFull C ON C.id = B.groupId\n" +
                "WHERE A.id = :userId";
        Query query = entityManager.createQuery(sql, SysGroupFull.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<SysGroupFull> findByMenuId(Long menuId) {
        var sql = "SELECT A.* FROM SYS_GROUP_FULL A\n" +
                "JOIN SYS_GROUP_MENU B ON A.ID = B.GROUP_ID\n" +
                "WHERE B.MENU_ID = :menuId";
        Map<String, Object> params = new HashMap<>();
        params.put("menuId", menuId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysGroupFull.class);
    }
    @Override
    public List<SysGroupFull> findNotInMenuId(Long menuId){
        var sql = "SELECT * FROM SYS_GROUP_FULL WHERE ID NOT IN(\n" +
                "    SELECT A.ID FROM SYS_GROUP_FULL A\n" +
                "    JOIN SYS_GROUP_MENU B ON A.ID = B.GROUP_ID\n" +
                "    WHERE B.MENU_ID = :menuId\n" +
                ")";
        Map<String, Object> params = new HashMap<>();
        params.put("menuId", menuId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysGroupFull.class);
    }

    @Override
    public List<SysGroupFull> getByUserName(String userName) {
        var sql = "SELECT * \n" +
                "FROM SYS_GROUP_Full sgf\n" +
                "INNER JOIN SYS_GROUP_USER sgu ON sgu.Group_id = sgf.id\n" +
                "INNER JOIN SYS_User su ON su.id = sgu.user_id where su.USERNAME = :userName";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysGroupFull.class);
    }

    @Override
    public List<SysGroupFull> getDonViCapCap(String donvi) {
        var sql = "SELECT * \n" +
                "FROM SYS_GROUP_Full sgf\n" +
                "INNER JOIN SYS_GROUP_DETAIL sgd ON sgd.Group_id = sgf.id\n";
        if ("MB".equalsIgnoreCase(donvi)){
            sql = sql + "where sgd.GROUP_TYPE  !=  4 and sgd.COMMITTEE = 28";
        }else if("AMC".equalsIgnoreCase(donvi)){
            sql = sql + "where sgd.GROUP_TYPE  =  4 and sgd.COMMITTEE = 28";
        }
        Map<String, Object> params = new HashMap<>();
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysGroupFull.class);
    }

//    @Override
//    public List<BranchModelDto> findAllBranch() {
//        var sql = "SELECT sd.BRANCH_CODE as branchCode, sg.NAME as branchName, sg.ID as id FROM SYS_GROUP_FULL sg " +
//                "INNER JOIN SYS_GROUP_DETAIL sd on sd.GROUP_ID = sg.ID " +
//                "WHERE sg.PARENT_ID=(SELECT a.id from SYS_GROUP_FULL a where a.key ='233_BDH_ChiNhanh')\n";
//        Map<String, Object> params = new HashMap<>();
//        return sqlQueryUtil.queryModel().queryForList(sql, params, BranchModelDto.class);
//    }

//    @Override
//    public List<SysUserModel> findUserDetail(Long id) {
//        var sql = "SELECT \n" +
//                "gf.Name as \"GROUP_NAME\",\n" +
//                "gf.PARENT_ID,\n" +
//                "gf.ID,\n" +
//                "r.Name  as \"ROLE_NAME\" \n" +
//                "FROM SYS_GROUP_USER gu\n" +
//                "JOIN SYS_GROUP_FULL gf on gf.ID = gu.GROUP_ID\n" +
//                "JOIN SYS_ROLE r on r.ID = gu.ROLE_ID\n" +
//                "WHERE gu.USER_ID =  :id ";
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", id);
//        return sqlQueryUtil.queryModel().queryForList(sql, params, SysUserModel.class);
//    }
}