package com.zn.cms.role.mapper;

import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.model.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface RoleMapper {

    Role roleDTOToRole(RoleDTO roleDTO);

    RoleDTO roleToRoleDTO(Role role);

    @BeforeMapping
    default void checkBefore(Role role, @MappingTarget RoleDTO roleDTO) {
        System.out.println("before mapping role to DTO");
    }

    @AfterMapping
    default void checkAfter(RoleDTO roleDTO, @MappingTarget Role role) {
        System.out.println("after mapping DTO to role");
    }

}
