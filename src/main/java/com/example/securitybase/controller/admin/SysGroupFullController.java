package com.example.securitybase.controller.admin;

import com.example.securitybase.comon.ErrorCode;
import com.example.securitybase.comon.MbConstants;
import com.example.securitybase.controller.bases.BaseController;
import com.example.securitybase.entity.SysGroupUser;
import com.example.securitybase.entity.SysUser;
import com.example.securitybase.response.ResponseData;
import com.example.securitybase.service.systems.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/administrator/sysGroupFull")
public class SysGroupFullController extends BaseController {

    @Autowired
    private SysGroupFullService service;
    @Autowired
    private SysGroupDetailService groupDetailService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysGroupRoleService groupRoleService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysGroupUserService groupUserService;
    @Autowired
    private SysRolePermissionService rolePermissionService;

    @Autowired
    private SysGroupMenuService groupMenuService;

    @Autowired
    private SysMenuService menuService;

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getAll(
    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findAll()));
    }

    @GetMapping("{id}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findById(id)));
    }

    @GetMapping("{id}/detail")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getDetailById(
            @PathVariable Long id
    ) {
        var data = groupDetailService.findByGroupId(id);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(data));
    }

    @GetMapping("{id}/roles")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getRolesByGroupId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(roleService.findByGroupId(id)));
    }

    @GetMapping("get-by-menuid")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getByMenuId(
            @RequestParam Long menuId
    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findByMenuId(menuId)));
    }

    @GetMapping("{id}/menus")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getMenusByGroupId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(menuService.findByGroupId(id)));
    }

    @GetMapping("{id}/roles/with-permission")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getRolesWithPermissionByGroupId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(roleService.findByGroupId(id, true)));
    }

    @PostMapping("{groupId}/{roleId}/users")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId,
            @RequestBody SysUser data

    ) {
        if (data == null || data.getId() == 0)
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));

        var listGroups = service.getByUserName(data.getUsername());
        if (listGroups == null || listGroups.isEmpty()) {
            var groupUser = new package com.mbbank.cmv.controller.administrator;

import com.mbbank.cmv.common.enums.ErrorCode;
import com.mbbank.cmv.controller.BaseController;
import com.mbbank.cmv.entity.SysPermission;
import com.mbbank.cmv.entity.SysRolePermission;
import com.mbbank.cmv.response.ResponseData;
import com.mbbank.cmv.service.systems.SysPermissionService;
import com.mbbank.cmv.service.systems.SysRolePermissionService;
import com.mbbank.cmv.service.systems.SysRoleService;
import com.mbbank.cmv.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.mbbank.cmv.common.MbConstants;
import java.util.List;

            @RestController
            @RequestMapping("/administrator/sysPermission")
            public class SysPermissionController extends BaseController {

                @Autowired
                private SysPermissionService service;
                @Autowired
                private SysRoleService sysRoleService;

                @Autowired
                private SysRolePermissionService sysRolePermissionService;

                @GetMapping
                public ResponseEntity<ResponseData<Object>> getAll(
                ) {
                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(service.findAll()));
                }
                @GetMapping("/active")
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> getAllActive(

                ) {
                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(service.findAllByActive(true)));
                }

                @GetMapping("{id}/roles")
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> getAllRoles(
                        @PathVariable Long id
                ) {
                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(sysRoleService.findByPermissionId(id)));
                }
                @PutMapping("{id}/roles/{roleId}")
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> putRole(
                        @PathVariable Long id,
                        @PathVariable Long roleId,
                        @RequestParam Boolean isActive
                ) {
                    var sysRolePermission = new SysRolePermission();
                    sysRolePermission.setPermissionId(id);
                    sysRolePermission.setRoleId(roleId);
                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(sysRolePermissionService.toggleSave(sysRolePermission, isActive)));
                }
                @PutMapping("{id}/roles")
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> putAll(
                        @PathVariable Long id,
                        @RequestParam Boolean isActive
                ) {

                    var sysRoles = sysRoleService.findAllByActive(true);
                    for(var sysRole : sysRoles){
                        var sysRolePermission = new SysRolePermission();
                        sysRolePermission.setPermissionId(id);
                        sysRolePermission.setRoleId(sysRole.getId());
                        sysRolePermissionService.toggleSave(sysRolePermission, isActive);
                    }



                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(true));
                }

                @GetMapping("{id}")
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> getById(
                        @PathVariable Long id
                ) {
                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(service.findById(id)));
                }
                @PutMapping
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> addOrUpdate(
                        @RequestBody SysPermission data
                ) {
                    if(StringUtil.isBlank(data.getPermissionKey())
                            || StringUtil.isBlank(data.getPermissionName()) )
                        return ResponseEntity.ok().body(new ResponseData<>()
                                .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));

                    data.setPermissionKey(StringUtil.replaceAllSpaces(data.getPermissionKey(), ""));
                    data.setPermissionName(StringUtil.replaceAllSpaces(data.getPermissionName(), ""));

                    if(service.checkExists(data)){
                        return ResponseEntity.ok().body(new ResponseData<>()
                                .error(ErrorCode.COMMON_DUPLICATE.getCode(), ErrorCode.COMMON_DUPLICATE.getMessage()));
                    }

                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(service.save(data)));
                }
                @DeleteMapping("{id}")
                @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
                public ResponseEntity<ResponseData<Object>> deleteById(
                        @PathVariable Long id

                ) {
                    service.deleteById(id);
                    return ResponseEntity.ok().body(new ResponseData<>()
                            .success(true));
                }
            }
            SysGroupUser();
            groupUser.setUserId(data.getId());
            groupUser.setGroupId(groupId);
            groupUser.setRoleId(roleId);
            groupUserService.save(groupUser);
            return ResponseEntity.ok().body(new ResponseData<>()
                    .success(true));
        }
        if (!listGroups.get(0).getId().equals(groupId)) {
            return ResponseEntity.ok().body(new ResponseData<>()
                    .success(false));
        }
        groupUserService.deleteByGroupIdAndRoleIdAndUserId(groupId, roleId, data.getId());

        var groupUser = new SysGroupUser();
        groupUser.setUserId(data.getId());
        groupUser.setGroupId(groupId);
        groupUser.setRoleId(roleId);
        groupUserService.save(groupUser);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(true));
    }

    @GetMapping("{groupId}/{roleId}/users")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getUsersByGroupAndRole(
            @PathVariable Long groupId,
            @PathVariable Long roleId

    ) {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(userService.findByGroupIdAndRoleId(groupId, roleId)));
    }

    @PutMapping("{groupId}/menus/{menuId}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addMenuToGroup(
            @PathVariable Long groupId,
            @PathVariable Long menuId,
            @RequestParam Boolean isActive
    ) {
        var data = new SysGroupMenu();
        data.setGroupId(groupId);
        data.setMenuId(menuId);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(groupMenuService.toggleSave(data, isActive)));
    }

    @PutMapping("{groupId}/menus/put-all")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addAllMenuToGroup(
            @PathVariable Long groupId
    ) {
        var listMenuNotInGroup = menuService.findNotInGroupId(groupId);
        if (listMenuNotInGroup != null && !listMenuNotInGroup.isEmpty()) {
            for (var item : listMenuNotInGroup) {
                var data = new SysGroupMenu();
                data.setGroupId(groupId);
                data.setMenuId(item.getId());
                groupMenuService.toggleSave(data, true);
            }
            return ResponseEntity.ok().body(new ResponseData<>()
                    .success(true));
        }
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(false));
    }

    @PutMapping("menus/{menuId}/put-all")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addAllGroupToMenu(
            @PathVariable Long menuId
    ) {
        var listMenuNotInGroup = service.findNotInMenuId(menuId);
        if (listMenuNotInGroup != null && !listMenuNotInGroup.isEmpty()) {
            for (var item : listMenuNotInGroup) {
                var data = new SysGroupMenu();
                data.setGroupId(item.getId());
                data.setMenuId(menuId);
                groupMenuService.toggleSave(data, true);
            }
            return ResponseEntity.ok().body(new ResponseData<>()
                    .success(service.findByMenuId(menuId)));
        }
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(new ArrayList<>()));
    }

    @DeleteMapping("{groupId}/{roleId}/users/{userId}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> removeUserFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId,
            @PathVariable Long userId

    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(groupUserService.deleteByGroupIdAndRoleIdAndUserId(groupId, roleId, userId)));
    }

    @GetMapping("{groupId}/users/not-in")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getUserNotInGroupId(
            @PathVariable Long groupId
    ) {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(userService.findNotInGroupId(groupId)));
    }

    @GetMapping("{groupId}/users/search-not-in")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getUserNotInGroupId(
            @PathVariable Long groupId,
            @RequestParam String username
    ) {
        var usernameTrim = "";
        if (username != null)
            usernameTrim = username.trim();
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(userService.findNotInGroupId(groupId, usernameTrim)));
    }

    @GetMapping("getByUsername")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getGroupByUsername(
            @RequestParam String username
    ) throws CmvBusinessException {
        var usernameTrim = "";
        if (username != null)
            usernameTrim = username.trim();

        var user = userService.findByUsername(usernameTrim);
        if (user == null) {
            throw new CmvBusinessException(ErrorCode.USER_NOT_FOUND);
        }

        var data = service.getByUserName(usernameTrim);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(data));
    }


    @PutMapping("{id}/roles")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> putRoles(
            @PathVariable Long id,
            @RequestBody List<Long> roles

    ) {
        groupRoleService.save(id, roles);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(true));
    }

    @PostMapping("/full")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addWithDetail(
            @RequestBody SysGroupFullDetailAto data

    ) {
        if (data.getSysGroupFull() == null)
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));


        var currentNode = data.getSysGroupFull();
        if (StringUtil.isNullOrEmpty(currentNode.getKey())
                || StringUtil.isNullOrEmpty(currentNode.getName())) {
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));

        }
        currentNode.setKey(StringUtil.replaceAllSpaces(currentNode.getKey(), ""));

        var dataByKey = service.findByKey(currentNode.getKey());
        if (dataByKey != null) {
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.SYS_GROUP_KEY_DUPLICATE.getCode(), ErrorCode.SYS_GROUP_KEY_DUPLICATE.getMessage()));
        }

        if (service.addNode(data.getSysGroupFull(), data.getSysGroupFull().getParentId())) {
            var detail = data.getSysGroupDetail();
            if (detail == null) {
                detail = new SysGroupDetail();
            }
            detail.setGroupId(currentNode.getId());
            groupDetailService.save(detail);
        }

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(data));
    }

    @PutMapping("/full")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> editWithDetail(
            @RequestBody SysGroupFullDetailAto data

    ) {
        if (data.getSysGroupFull() == null)
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));

        var currentNode = data.getSysGroupFull();
        service.save(data.getSysGroupFull());

        var detail = data.getSysGroupDetail();
        if (detail == null) {
            detail = new SysGroupDetail();
        }
        detail.setGroupId(currentNode.getId());
        groupDetailService.save(detail);

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(data));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> delete(
            @PathVariable Long id
    ) throws Exception {
        var result = service.removeNode(id);
        if (result) {
            groupDetailService.deleteByGroupId(id);
        }

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(result));
    }

    @PostMapping("/moveDepartment/{id}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> moveDepartment(@PathVariable Long id) throws CmvBusinessException {
        return ResponseEntity.ok().body(new ResponseData<>().success(service.moveDepartment(id)));
    }
}
