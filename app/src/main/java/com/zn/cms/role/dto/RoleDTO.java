package com.zn.cms.role.dto;

import com.zn.cms.permission.model.Permission;
import com.zn.cms.role.model.Role;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoleDTO {

    private String name;
    private Collection<Permission> permissions;

    public RoleDTO(Role role) {
        this.name = role.getName();
        this.permissions = this.getPermissions();
    }
}
