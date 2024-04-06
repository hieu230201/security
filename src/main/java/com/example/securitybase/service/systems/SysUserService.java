package com.example.securitybase.service.systems;

import java.util.List;

import com.example.securitybase.entity.SysUser;
import com.example.securitybase.exception.CustomServiceBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserService extends GenericSevice<SysUser, Long> {

	Page<SysUser> findAllByPaging(String username, Pageable pageable);

	SysUserWithCaModel findWithCaByUserId(Long userId);
	SysUserWithCaModel saveWithCa(SysUserWithCaModel model);
	SysUser findByUserId(Long userId);
	List<SysUser> findByGroupIdAndRoleId(Long groupId, Long roleId);
	List<SysUser> findInParentGroupByGroupTypeAndRole(Long userId, String groupType,Long roleId);
	List<SysUser> findInParentGroupByGroupTypeAndRole(Long userId, String groupType, String roleKey);
	List<SysUser> findAllGdChiNhanh(Long userId);
	List<SysUser> findAllPhoGdChiNhanh(Long userId);
	List<SysUser> findByGroupId(Long groupId);
	List<SysUser> findNotInGroupId(Long groupId);
	List<SysUser> findNotInGroupId(Long groupId, String username);
	Boolean isUserIdInGroupId(Long groupId, Long userId);

	boolean checkExists(String username);
	Boolean checkExists(SysUser data);

	List<SysUser> findManages(Long groupId);
	List<SysUser> findManagesHighLevel(Long groupId, Long userId, boolean forceJumpLevel);
	SysUser findByUsername(String username);

	List<SysUser> findEmployeesByManagerId(Long userId);
	boolean isLeader(Long userId);

	List<SysUser> getListUserApprovedByRoleAndUserCV (Long userStaffId,String roleStaffId, String roleApprovedId);
	List<SysUser> getListUserApprovedByListRoleAndUserCV (Long userStaffId, List<String> roleStaffKey, String roleApprovedKey);
	List<SysUser> getListUserPheDuyet(Long userId, String donViDinhGiaId);
    List<SysUser> getListUserApproved(Long userStaffId, List<String> roleStaffKey,  List<String> roleApprovedKey);
	List<SysUser> updateRoleUser (List<SysUser> list);

	List<SysUser> findByLikeUsername (String username) throws CustomServiceBusinessException;

	String getRoleByUserName (String username) throws CustomServiceBusinessException;

	String getBranchByUser (String username, String key);
}

