package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysGroupUser;

public interface SysGroupUserService extends GenericSevice<SysGroupUser, Long> {
    Long deleteByGroupIdAndRoleIdAndUserId(Long groupId, Long roleId, Long userId);
    SysGroupUser findByGroupIdAndRoleIdAndUserId(Long groupId, Long roleId, Long userId);
    void updateByGroupId(Long groupId, Long newGroupId);
}