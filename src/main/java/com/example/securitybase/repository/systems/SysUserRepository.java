package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>, SysUserRepositoryCustom {
	SysUser findById(long id);
	SysUser findByUsername(String username);

	List<SysUser> findByUsernameLike(String username);
}