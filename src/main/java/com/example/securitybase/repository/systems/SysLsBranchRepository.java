package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysLsBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLsBranchRepository extends JpaRepository<SysLsBranch, Long>{
	SysLsBranch findBybranchid(String branchid);
}