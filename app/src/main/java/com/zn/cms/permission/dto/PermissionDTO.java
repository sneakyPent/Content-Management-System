package com.zn.cms.permission.dto;

import com.zn.cms.permission.model.Permission;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PermissionDTO {

    private String name;

    public PermissionDTO(Permission permission){
        this.name = permission.getName();
    }
}
