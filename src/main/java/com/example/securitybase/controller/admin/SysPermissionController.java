package com.example.securitybase.controller.admin;

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
