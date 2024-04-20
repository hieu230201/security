package com.example.securitybase.service.systems;


import com.example.securitybase.entity.SysUserCa;
import com.example.securitybase.model.atos.clones.bases.SysUserCaAto;

import java.util.List;

public interface SysUserCaService extends GenericSevice<SysUserCa, Long> {
	SysUserCaAto findByUserName(String userName);

	SysUserCaAto findByEmail(String email);

	List<SysUserCaAto> findAllData();
}