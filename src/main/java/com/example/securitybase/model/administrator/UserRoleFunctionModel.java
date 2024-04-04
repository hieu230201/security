package com.example.securitybase.model.administrator;

import lombok.Data;
import com.example.securitybase.entity.*;
import java.util.List;

@Data
public class UserRoleFunctionModel {

    private String roleKey;
    private String roleName;
    List<SysRoleFunction> roleFunctions;
}