package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysRoleFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleFunctionRepository extends JpaRepository<SysRoleFunction, Long>, SysRoleFunctionRepositoryCustom
{
    List<SysRoleFunction> findByRoleId(Long roleId);
}