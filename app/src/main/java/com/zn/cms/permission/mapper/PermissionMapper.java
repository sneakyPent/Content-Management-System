package com.zn.cms.permission.mapper;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.model.Permission;
import org.mapstruct.*;


@Mapper(componentModel = "Spring")
public interface PermissionMapper {

    @Mapping(target = "roles", ignore = true)
    Permission permissionDTOToPermission(PermissionDTO permissionDTO);

    PermissionDTO permissionToPermissionDTO(Permission permission);

    @BeforeMapping
    default void checkBefore(Permission permission, @MappingTarget PermissionDTO permissionDTO) {
        System.out.println("before mapping permission to DTO");
    }

    @AfterMapping
    default void checkAfter(PermissionDTO permissionDTO, @MappingTarget Permission permission) {
        System.out.println("after mapping DTO to Permission");
    }
}
