package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysGroupRoleRepository extends JpaRepository<SysGroupRole, Long> {
    List<SysGroupRole> findByGroupId(Long groupId);
    void deleteByGroupId(Long groupId);

    @Modifying
    @Query(value="UPDATE SYS_GROUP_ROLE SET GROUP_ID = :newGroupId WHERE GROUP_ID = :groupId",nativeQuery = true)
    void updateByGroupId(Long groupId, Long newGroupId);
}