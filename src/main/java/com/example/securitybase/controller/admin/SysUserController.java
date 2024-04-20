package com.example.securitybase.controller.admin;

import com.example.securitybase.comon.ErrorCode;
import com.example.securitybase.comon.MbConstants;
import com.example.securitybase.comon.enums.StatusType;
import com.example.securitybase.comon.enums.SysUserRole;
import com.example.securitybase.controller.bases.BaseController;
import com.example.securitybase.entity.SysGroupUser;
import com.example.securitybase.entity.SysUser;
import com.example.securitybase.model.administrator.SysUserWithCaModel;
import com.example.securitybase.response.ResponseData;
import com.example.securitybase.service.systems.SysGroupUserService;
import com.example.securitybase.service.systems.SysUserService;
import com.example.securitybase.util.ModelMapperUtil;
import com.example.securitybase.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator/sysUser")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService service;

    @Autowired
    private SysGroupUserService sysGroupUserService;

    @Value("${groupKiemKe:7188}")
    private String groupKiemKe;

    @Value("${roleKiemKe:152}")
    private String roleKiemKe;

    @GetMapping("/paging")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getAllPaging(@RequestParam(required = false) String username,
                                                             @RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String sortType) {

        if (page == null || page < 0) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        PageRequest pageRequest;
        if (sortBy == null || sortBy.isBlank()) {
            pageRequest = PageRequest.of(page, size);
        } else {
            if (sortType.equals("descending")) {
                pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
            } else {
                pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
            }
        }

        var record = service.findAllByPaging(username, pageRequest);
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(record));
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getAll() {

        List<SysUser> sysUsers = service.findAll();
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(sysUsers));
    }

    @GetMapping("userStatus")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getUserStatus() {

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(StatusType.values()));
    }

    @GetMapping("{id}")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findById(id)));
    }

    @GetMapping("{id}/with-ca")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getCaById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findWithCaByUserId(id)));
    }

    @GetMapping("find")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getByUsername(
            @RequestParam String username
    ) {
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.findByUsername(username)));
    }

    @PutMapping
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> save(
            @RequestBody SysUser data
    ) {
        if (StringUtil.isBlank(data.getUsername()))
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));

        data.setUsername(StringUtil.replaceAllSpaces(data.getUsername(), "").toLowerCase(Locale.ROOT));

        if (service.checkExists(data)) {
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_DUPLICATE.getCode(), ErrorCode.COMMON_DUPLICATE.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.save(data)));
    }

    @PostMapping(value = "with-ca", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.ADMIN_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> saveWithCa(
            @ModelAttribute SysUserWithCaModel data
    ) {
        if (StringUtil.isBlank(data.getUsername()))
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_REQUIRE_FIELD.getCode(), ErrorCode.COMMON_REQUIRE_FIELD.getMessage()));

        data.setUsername(StringUtil.replaceAllSpaces(data.getUsername(), "").toLowerCase(Locale.ROOT));

        var sysUser = ModelMapperUtil.toObject(data, SysUser.class);

        if (service.checkExists(sysUser)) {
            return ResponseEntity.ok().body(new ResponseData<>()
                    .error(ErrorCode.COMMON_DUPLICATE.getCode(), ErrorCode.COMMON_DUPLICATE.getMessage()));
        }
        return ResponseEntity.ok().body(new ResponseData<>()
                .success(service.saveWithCa(data)));
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

    @GetMapping("getUserPheDuyet")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.QLTS_MODIFY + "')")
    public ResponseEntity<ResponseData<Object>> getUserPheDuyet() {
        List<String> listRoleId = new ArrayList<>();
        listRoleId.add(SysUserRole.MB_CHUYEN_VIEN_QLTSDB.getValue());
        listRoleId.add(SysUserRole.MB_ADMIN_QLTSDB.getValue());
        var result = service.getListUserApprovedByListRoleAndUserCV(getUserId(), listRoleId, SysUserRole.MB_KIEM_SOAT_QLTSDB.getValue());
        return ResponseEntity.ok().body(new ResponseData<>().success(result));
    }

    @GetMapping("/GetListUserPheDuyet")
    @PreAuthorize("#oauth2.hasScope('" + MbConstants.PERMISSION.USER_VIEW + "')")
    public ResponseEntity<ResponseData<Object>> getListUserPheDuyet(String donViDinhGiaId) {
        return ResponseEntity.ok().body(new ResponseData<>().success(service.getListUserPheDuyet(getUserId(), donViDinhGiaId)));
    }


    @PostMapping("/saveKiemKe")
    public ResponseEntity<ResponseData<Object>> saveKiemKe(@RequestParam String data) {
        SysUser user = service.findByUsername(data);

        if (user == null) {
            user = new SysUser();
            user.setUsername(data);
            user.setEmail(data + "@mbbank.com.vn");
            user.setStatus("NORMAL");
            user.setCreatedBy("CMVSTORE");
            user.setCreatedDate(new Date());
            service.save(user);
        }

        SysGroupUser sysGroupUser = sysGroupUserService.findByGroupIdAndRoleIdAndUserId(Long.parseLong(groupKiemKe), Long.parseLong(roleKiemKe), user.getId());
        if (sysGroupUser == null) {
            sysGroupUser = new SysGroupUser();
            sysGroupUser.setGroupId(Long.parseLong(groupKiemKe));
            sysGroupUser.setRoleId(Long.parseLong(roleKiemKe));
            sysGroupUser.setUserId(user.getId());
            sysGroupUserService.save(sysGroupUser);
        }

        return ResponseEntity.ok().body(new ResponseData<>()
                .success(user));
    }
}
