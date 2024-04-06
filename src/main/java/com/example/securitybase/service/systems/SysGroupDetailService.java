package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysGroupDetail;

import java.util.List;

public interface SysGroupDetailService extends GenericSevice<SysGroupDetail, Long> {
    SysGroupDetail findByGroupId(Long groupId);
    Long deleteByGroupId(Long groupId);
}