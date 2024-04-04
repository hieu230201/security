package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysGroupFull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysGroupFullRepositoryCustom {
    List<SysGroupFull> getAllLeafNode();

    Boolean extendNode(SysGroupFull parentNode);
    Boolean addNode(SysGroupFull node, SysGroupFull parentNode);
    Boolean removeNode(SysGroupFull node);

    List<Long> getAllParentId(SysGroupFull node);
    List<SysGroupFull> getAllParentNode(SysGroupFull node);
    List<SysGroupFull> getAllParentNodeByGroupType(SysGroupFull node, String groupTypeParent);

    List<SysGroupFull> getAllChildNode(SysGroupFull parentNode);

    List<SysGroupFull> findByUserId(Long userId);

    List<SysGroupFull> findByMenuId(Long menuId);
    List<SysGroupFull> findNotInMenuId(Long menuId);

    List<SysGroupFull> getByUserName(String userName);

    List<SysGroupFull> getDonViCapCap(String donvi);

//    List<BranchModelDto> findAllBranch();
//
//    List<SysUserModel> findUserDetail(Long id);

}