package com.example.securitybase.controller.admin;


import com.example.securitybase.comon.MbConstants;
import com.example.securitybase.controller.bases.BaseController;
import com.example.securitybase.entity.SysMenu;
import com.example.securitybase.response.ResponseData;
import com.example.securitybase.service.systems.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/administrator/sysMenu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService menuService;


    @GetMapping
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getAllMenu() {

        List<SysMenu> sysMenus =  menuService.findAllByOrderBySort();
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(sysMenus));
    }

    @PutMapping
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> saveMenu(
            @RequestBody List<SysMenu> menus) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(menuService.saveAndReplaceAll(menus)));
    }

}
