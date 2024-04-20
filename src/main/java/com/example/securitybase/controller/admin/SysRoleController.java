package com.example.securitybase.controller.admin;

import com.mbbank.cmv.common.enums.ErrorCode;
import com.mbbank.cmv.controller.BaseController;
import com.mbbank.cmv.controller.bases.BaseCrudController;
import com.mbbank.cmv.entity.SysGroupMenu;
import com.mbbank.cmv.entity.SysRole;
import com.mbbank.cmv.entity.SysRoleMenu;
import com.mbbank.cmv.response.ResponseData;
import com.mbbank.cmv.service.GenericSevice;
import com.mbbank.cmv.service.systems.SysMenuService;
import com.mbbank.cmv.service.systems.SysRoleMenuService;
import com.mbbank.cmv.service.systems.SysRoleService;
import com.mbbank.cmv.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.mbbank.cmv.common.MbConstants;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/administrator/sysRole")
public class SysRoleController extends BaseCrudController<SysRole> {

    @Autowired
    private SysRoleService service;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    protected GenericSevice<SysRole, Long> getService() {
        return service;
    }

    @GetMapping("/active")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getAllActive(

    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findAllByActive(true)));
    }
    @Override
    @PutMapping
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addOrUpdate(
            @RequestBody SysRole data

    ) {
        if(StringUtil.isBlank(data.getKey())){
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));
        }
        data.setKey(StringUtil.replaceAllSpaces(data.getKey(), ""));
        if(service.checkExists(data)){
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_DUPLICATE.getCode(), ErrorCode.COMMON_DUPLICATE.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.save(data)));
    }

    @GetMapping("/list-function")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getAllFunction(
            @RequestParam(required = false) Long userId
    ) {
        var finalUserId = userId;
        if(finalUserId == null || finalUserId == 0){
            finalUserId = getUserId();
        }
        var result = service.findFunctionsByUserId(finalUserId);
        return ResponseEntity.ok().body(new ResponseData<>().success(result));
    }
    @GetMapping("/check-exists-function")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> checkExistsFunction(
            @RequestParam String functionName,
            @RequestParam(required = false) Long userId
    ) {
        var finalUserId = userId;
        if(finalUserId == null || finalUserId == 0){
            finalUserId = getUserId();
        }

        var result = service.checkUserHasFunction(functionName, finalUserId);
        return ResponseEntity.ok().body(new ResponseData<>().success(result));
    }

    @GetMapping("{id}/menus")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getMenusByGroupId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(sysMenuService.findByRoleId(id)));
    }

    @PutMapping("{roleId}/menus/{menuId}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addMenuToRole(
            @PathVariable Long roleId,
            @PathVariable Long menuId,
            @RequestParam Boolean isActive
    ) {
        var data = new SysRoleMenu();
        data.setRoleId(roleId);
        data.setMenuId(menuId);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(sysRoleMenuService.toggleSave(data, isActive)));
    }
    @PutMapping("{roleId}/menus/put-all")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> addAllMenuToRole(
            @PathVariable Long roleId
    ){
        var listNotInRole = sysMenuService.findNotInRoleId(roleId);
        if(listNotInRole != null && !listNotInRole.isEmpty()){
            for (var item : listNotInRole){
                var data = new SysRoleMenu();
                data.setRoleId(roleId);
                data.setMenuId(item.getId());
                sysRoleMenuService.toggleSave(data, true);
            }
            return ResponseEntity.ok().body(new ResponseData<>()
                    .success(true));
        }
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(false));
    }

}
