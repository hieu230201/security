package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long>, SysRoleRepositoryCustom
//        BaseActiveFieldRepository<SysRole>
{
    SysRole findByKey(String key);
}