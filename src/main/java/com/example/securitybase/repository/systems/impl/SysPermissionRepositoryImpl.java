package com.example.securitybase.repository.systems.impl;


import com.example.securitybase.entity.SysPermission;
import com.example.securitybase.repository.systems.SysPermissionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class SysPermissionRepositoryImpl implements SysPermissionRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;


	@Override
    public List<SysPermission> findByGroupId(Long groupId) {
	    List<Long> groupIds = new ArrayList<>();
	    groupIds.add(groupId);
	    return findByGroupIds(groupIds);
    }

	@Override
    public List<SysPermission> findByGroupIds(List<Long> groupIds) {
        var sql = "SELECT DISTINCT E FROM SysGroupFull A\n" +
                "JOIN SysGroupRole B ON A.id = B.groupId\n" +
                "JOIN SysRole C ON B.roleId = C.id\n" +
                "JOIN SysRolePermission D ON C.id = D.roleId AND A.id = D.groupId\n" +
                "JOIN SysPermission E ON E.id = D.permissionId\n" +
                "WHERE A.id IN (:groupIds) AND C.active = TRUE AND E.active = TRUE";
        Query query = entityManager.createQuery(sql, SysPermission.class);
        query.setParameter("groupIds", groupIds);

        //noinspection unchecked
        return query.getResultList();
    }
    @Override
    public List<SysPermission> findByGroupIdAndRoleId(Long groupId, Long roleId) {
        var sql = "SELECT DISTINCT E FROM SysGroupFull A\n" +
                "JOIN SysGroupRole B ON A.id = B.groupId\n" +
                "JOIN SysRole C ON B.roleId = C.id\n" +
                "JOIN SysRolePermission D ON C.id = D.roleId AND A.id = D.groupId\n" +
                "JOIN SysPermission E ON E.id = D.permissionId\n" +
                "WHERE A.id = :groupId AND C.id = :roleId AND C.active = TRUE AND E.active = TRUE";
        Query query = entityManager.createQuery(sql, SysPermission.class);
        query.setParameter("groupId", groupId);
        query.setParameter("roleId", roleId);

        //noinspection unchecked
        return query.getResultList();
    }
    @Override
    public List<SysPermission> findByUserId(Long userId) {
        var sql = "SELECT DISTINCT E FROM SysUser A\n" +
                "JOIN SysGroupUser B ON A.id = B.userId\n" +
                "JOIN SysRole F ON B.roleId = F.id\n" +
                "JOIN SysGroupRole C ON B.roleId = C.roleId\n" +
                "JOIN SysRolePermission D ON C.roleId = D.roleId\n" +
                "JOIN SysPermission E ON D.permissionId = E.id\n" +
                "WHERE A.id = :userId AND F.active = TRUE AND E.active = TRUE";
        Query query = entityManager.createQuery(sql, SysPermission.class);
        query.setParameter("userId", userId);

        //noinspection unchecked
        return query.getResultList();
    }
}

