package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysUserCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SysUserCaRepository extends JpaRepository<SysUserCa, Long>{
	SysUserCa findByUserName(String userName);
	SysUserCa findByEmail(String email);
}