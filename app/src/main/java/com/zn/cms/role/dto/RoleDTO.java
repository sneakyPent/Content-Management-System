package com.zn.cms.role.dto;

import com.zn.cms.permission.dto.PermissionDTO;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoleDTO {

    private String name;
    private Collection<PermissionDTO> permissions;

}
