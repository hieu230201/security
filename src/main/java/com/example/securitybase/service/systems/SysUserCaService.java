package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysUserCa;

public interface SysUserCaService extends GenericSevice<SysUserCa, Long> {
	SysUserCaAto findByUserName(String userName);

	SysUserCaAto findByEmail(String email);

	List<SysUserCaAto> findAllData();
}