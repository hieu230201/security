package com.example.securitybase.repository.systems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysGroupFullRepository extends JpaRepository<SysGroupFull, Long>, SysGroupFullRepositoryCustom {
    SysGroupFull findByKey(String key);

    @Query(value = "SELECT sgd.BRANCH_CODE FROM SYS_USER su " +
            "JOIN SYS_GROUP_USER sgu ON su.ID = sgu.USER_ID " +
            "JOIN SYS_GROUP_DETAIL sgd ON  sgd.GROUP_ID =sgu.GROUP_ID " +
            "WHERE sgu.ROLE_ID = :roleId AND su.USERNAME = :userName", nativeQuery = true)
    String findBranchCodeByRoleIdAndUserName (@Param("roleId") Long roleId, @Param("userName") String username);
}