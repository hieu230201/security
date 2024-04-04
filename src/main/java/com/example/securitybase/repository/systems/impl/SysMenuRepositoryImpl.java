package com.example.securitybase.repository.systems.impl;

import com.example.securitybase.entity.SysMenu;
import com.example.securitybase.repository.systems.SysMenuRepositoryCustom;
import com.example.securitybase.util.SqlQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysMenuRepositoryImpl implements SysMenuRepositoryCustom {

    @Autowired
    SqlQueryUtil sqlQueryUtil;

//    @UseLogging
//    @UseSafeRunning
	@Override
    public List<SysMenu> findByUserId(Long userId) {
        var sql = "SELECT DISTINCT A.* FROM SYS_MENU A\n" +
                "JOIN SYS_ROLE_MENU B ON B.MENU_ID = A.ID\n" +
                "JOIN SYS_GROUP_USER C ON C.ROLE_ID = B.ROLE_ID\n" +
                "WHERE C.USER_ID = :userId ORDER BY A.SORT";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysMenu.class);
    }

    @Override
    public List<SysMenu> findByGroupId(Long groupId) {
/*
        var sql = "SELECT C FROM SysGroupFull A\n" +
                "INNER JOIN SysGroupMenu B ON A.id = B.groupId\n" +
                "INNER JOIN SysMenu C ON C.id = B.menuId\n" +
                "WHERE A.id = :groupId";
        Query query = entityManager.createQuery(sql, SysMenu.class);
        query.setParameter("groupId", groupId);


        return query.getResultList();
*/
        //Sql opt notEqualWith uncheck SysGroup
        var sql = "SELECT * FROM SYS_MENU A\n" +
                "JOIN SYS_GROUP_MENU B ON A.ID = B.MENU_ID\n" +
                "WHERE B.GROUP_ID = :groupId";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysMenu.class);
    }
    @Override
    public List<SysMenu> findNotInGroupId(Long groupId){
        var sql = "SELECT * FROM SYS_MENU WHERE ID NOT IN(\n" +
                "    SELECT A.ID FROM SYS_MENU A\n" +
                "    JOIN SYS_GROUP_MENU B ON A.ID = B.MENU_ID\n" +
                "    WHERE B.GROUP_ID = :groupId\n" +
                ")";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysMenu.class);
    }

    @Override
    public List<SysMenu> findByRoleId(Long roleId) {
        var sql = "SELECT * FROM SYS_MENU A\n" +
                "JOIN SYS_ROLE_MENU B ON A.ID = B.MENU_ID\n" +
                "WHERE B.ROLE_ID = :roleId";
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysMenu.class);
    }

    @Override
    public List<SysMenu> findNotInRoleId(Long roleId) {
        var sql = "SELECT * FROM SYS_MENU WHERE ID NOT IN(\n" +
                "    SELECT A.ID FROM SYS_MENU A\n" +
                "    JOIN SYS_ROLE_MENU B ON A.ID = B.MENU_ID\n" +
                "    WHERE B.ROLE_ID = :roleId\n" +
                ")";
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        return sqlQueryUtil.queryEntity().querySql(sql, params, SysMenu.class);
    }
}