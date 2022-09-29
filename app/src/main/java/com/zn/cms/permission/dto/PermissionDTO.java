package com.zn.cms.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PermissionDTO {

    private String name;

    private Collection<PermissionDTO> dependsOnPermissions;
}
