package com.example.securitybase.service.systems.impl;

import java.util.List;

import com.example.securitybase.entity.SysUserCa;
import com.example.securitybase.model.atos.clones.bases.SysUserCaAto;
import com.example.securitybase.repository.systems.SysUserCaRepository;
import com.example.securitybase.service.systems.AbstractGenericService;
import com.example.securitybase.service.systems.SysUserCaService;
import com.example.securitybase.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class SysUserCaServiceImpl extends AbstractGenericService<SysUserCa, Long> implements SysUserCaService {
	@Autowired
	private SysUserCaRepository sysUserCaRepository;

	@Override
	protected JpaRepository<SysUserCa, Long> getRepository() {
		return sysUserCaRepository;
	}

	@Override
	public SysUserCaAto findByUserName(String userName) {
		SysUserCa sysUserCa = sysUserCaRepository.findByUserName(userName);
		return new SysUserCaAto().fromEntity(sysUserCa);
	}

	@Override
	public SysUserCaAto findByEmail(String email) {
		SysUserCa sysUserCa = sysUserCaRepository.findByEmail(email);
		return new SysUserCaAto().fromEntity(sysUserCa);
	}

	@Override
	public List<SysUserCaAto> findAllData() {
		List<SysUserCa> lstSysUserCa = findAll();
		List<SysUserCaAto> lstSysUserCaAto = ModelMapperUtil.listObjectToListModel(lstSysUserCa, SysUserCaAto.class);
		return lstSysUserCaAto;
	}

}

