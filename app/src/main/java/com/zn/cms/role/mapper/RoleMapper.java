package com.zn.cms.role.mapper;

import com.zn.cms.permission.mapper.PermissionMapper;
import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.model.Role;
import org.mapstruct.*;

@Mapper(componentModel = "Spring",
        uses = {PermissionMapper.class
        })
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
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
