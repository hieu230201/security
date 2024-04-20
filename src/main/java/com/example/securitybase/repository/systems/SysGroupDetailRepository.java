package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysGroupDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysGroupDetailRepository extends JpaRepository<SysGroupDetail, Long> {
    SysGroupDetail findByGroupId(Long groupId);
    Long deleteByGroupId(Long groupId);
}
