package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysGroupRole;

import java.util.List;

public interface SysGroupRoleService extends GenericSevice<SysGroupRole, Long> {
    void save(Long groupId, List<Long> roleIds);

    void deleteByGroupId(Long groupId);

    void updateByGroupId(Long groupId, Long newGroupId);
}