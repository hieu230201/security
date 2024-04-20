package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysGroupUserRepository extends JpaRepository<SysGroupUser, Long> {
    List<SysGroupUser> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    Long deleteByGroupIdAndRoleIdAndUserId(Long groupId, Long roleId, Long userId);

    SysGroupUser findByGroupIdAndRoleIdAndUserId(Long groupId, Long roleId, Long userId);

    @Modifying
    @Query(value="UPDATE SYS_GROUP_USER SET GROUP_ID = :newGroupId WHERE GROUP_ID = :groupId",nativeQuery = true)
    void updateByGroupId(Long groupId, Long newGroupId);
}