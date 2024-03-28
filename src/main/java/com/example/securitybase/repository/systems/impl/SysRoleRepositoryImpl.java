package com.example.securitybase.repository.systems.impl;

import com.example.securitybase.util.SqlQueryUtil;
import com.mbbank.cmv.common.anotations.UseLogging;
import com.mbbank.cmv.common.anotations.UseSafeRunning;
import com.mbbank.cmv.repository.systems.SysRoleRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SysRoleRepositoryImpl implements SysRoleRepositoryCustom {


    @Autowired
    SqlQueryUtil sqlQueryUtil;

    @UseSafeRunning
    @UseLogging
    @SuppressWarnings("unchecked")
    @Override
    public List<SysRole> findByGroupId(Long groupId) {
        return findByGroupIds(groupId);
    }

    @UseSafeRunning
    @UseLogging
    @Override
    public List<SysRole> findByGroupIdAndUserId(Long groupId, Long userId) {
        var sql = "SELECT A FROM SysRole A\n" +
                "JOIN SysGroupRole B ON B.roleId = A.id\n" +
                "JOIN SysGroupFull C ON C.id = B.groupId\n" +
                "JOIN SysGroupUser D ON D.groupId = C.id AND D.roleId = A.id\n" +
                "WHERE C.id = :groupId AND D.userId = :userId AND A.active = TRUE";

        var params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("userId", userId);

        return sqlQueryUtil.queryEntity().queryHql(sql, params, SysRole.class);
    }

    @Override
    public List<SysRole> findByUserId(Long userId) {
        var sqlQuery = "SELECT A.* FROM SYS_ROLE A\n" +
                "JOIN SYS_GROUP_ROLE B ON A.ID = B.ROLE_ID\n" +
                "JOIN SYS_GROUP_FULL C ON C.ID = B.GROUP_ID\n" +
                "JOIN SYS_GROUP_USER D ON C.ID = D.GROUP_ID\n" +
                "JOIN SYS_USER E ON E.ID = D.USER_ID\n" +
                "WHERE E.ID = :userId";
        var params = new HashMap<String, Object>();
        params.put("userId", userId);

        return sqlQueryUtil.queryEntity().querySql(sqlQuery, params, SysRole.class);
    }

    @Override
    public List<SysRole> findByUserIdNew(Long userId) {
        var sqlQuery = "SELECT C.* FROM SYS_USER A\n" +
                "JOIN SYS_GROUP_USER B ON B.USER_ID = :userId\n" +
                "JOIN SYS_ROLE C ON C.ID = B.ROLE_ID\n" +
                "WHERE A.ID = :userId\n";
        var params = new HashMap<String, Object>();
        params.put("userId", userId);

        return sqlQueryUtil.queryEntity().querySql(sqlQuery, params, SysRole.class);
    }

    @Override
    public List<String> findFunctionsByUserId(Long userId) {
        var sql = "SELECT DISTINCT A.FUNCTION_NAME FROM SYS_ROLE_FUNCTION A\n" +
                "JOIN SYS_GROUP_USER B ON B.ROLE_ID = A.ROLE_ID\n" +
                "WHERE B.USER_ID = :userId";
        var params = new HashMap<String, Object>();
        params.put("userId",userId);
        return sqlQueryUtil.queryModel().queryForList(sql,params, String.class);
    }
    @Override
    public List<String> findFunctionsByRoleId(Long roleId){
        var sql = "SELECT DISTINCT A.FUNCTION_NAME FROM SYS_ROLE_FUNCTION A\n" +
                "JOIN SYS_GROUP_USER B ON B.ROLE_ID = A.ROLE_ID\n" +
                "WHERE B.ROLE_ID = :roleId";
        var params = new HashMap<String, Object>();
        params.put("roleId",roleId);
        return sqlQueryUtil.queryModel().queryForList(sql, params, String.class);
    }

    @Override
    public boolean checkUserHasFunction(String functionName, Long userId) {
        return findFunctionsByUserId(userId).stream().anyMatch(x-> Objects.equals(x, functionName));
    }

    @Override
    public boolean checkRoleHasFunction(String functionName, Long roleId) {
        return findFunctionsByRoleId(roleId).stream().anyMatch(x-> Objects.equals(x, functionName));
    }


    @Override
    public List<SysRole> findByPermissionId(Long permissionId) {
        var sql = "SELECT DISTINCT * FROM SYS_ROLE A\n" +
                "JOIN SYS_ROLE_PERMISSION B ON A.ID = B.ROLE_ID\n" +
                "JOIN SYS_PERMISSION C ON B.PERMISSION_ID = C.ID\n" +
                "WHERE A.IS_ACTIVE = 1 AND C.IS_ACTIVE = 1 AND C.ID = :permissionId";
        var params = new HashMap<String, Object>();
        params.put("permissionId", permissionId);

        return sqlQueryUtil.queryEntity().querySql(sql, params, SysRole.class);
    }

    @UseSafeRunning
    @UseLogging
    @Override
    public List<SysRole> findByGroupIds(Long... groupIds) {
        var sql = "SELECT C FROM SysGroupFull A\n" +
                "INNER JOIN SysGroupRole B ON A.id = B.groupId\n" +
                "INNER JOIN SysRole C ON C.id = B.roleId\n" +
                "WHERE A.id IN (:groupIds) AND C.active = true";


        var params = new HashMap<String, Object>();
        params.put("groupIds", Arrays.asList(groupIds));
        return sqlQueryUtil.queryEntity().queryHql(sql, params, SysRole.class);
    }


}