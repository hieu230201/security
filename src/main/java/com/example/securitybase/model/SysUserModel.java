package com.example.securitybase.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class SysUserModel {
    @JsonProperty("GROUP_NAME")
    @JsonAlias("GROUP_NAME")
    private String groupName;

    @JsonProperty("ROLE_NAME")
    @JsonAlias("ROLE_NAME")
    private String roleName;

    @JsonProperty("PARENT_ID")
    @JsonAlias("PARENT_ID")
    private Long parentId;

    @JsonProperty("ID")
    @JsonAlias("ID")
    private Long id;
}

