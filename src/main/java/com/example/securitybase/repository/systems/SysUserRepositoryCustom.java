package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepositoryCustom {
    Page<SysUser> findAllByPaging(String username, Pageable pageable);

    List<SysUser> findByGroupIdAndRoleId(Long groupId, Long roleId);
    List<SysUser> findByGroupId(Long groupId);
    List<SysUser> findNotInGroupId(Long groupId);
    List<SysUser> findNotInGroupId(Long groupId, String username);
    Boolean isUserIdInGroupId(Long groupId, Long userId);
    List<SysUser> findManages(Long groupId);
    List<SysUser> findManagesHighLevel(Long groupId, Long userId, boolean forceJumpLevel);
    List<SysUser> findEmployeesByManagerId(Long userId);
    boolean isLeader(Long userId);
    List<String> findAllUserByGroupKey(String groupKey);
    List<String> findAllUserInGroup(SysGroupFull sysGroup);
    List<SysUser> getListUserApprovedByRoleAndUserCV (Long userStaffId, String roleStaffId, String roleApprovedId);
    List<SysUser> getListUserApprovedByListRoleAndUserCV (Long userStaffId, List<String> roleStaffKey, String roleApprovedKey);
    List<SysUser> getListUserApproved (Long userStaffId, List<String> roleStaffKey, List<String> roleApprovedKey);

}