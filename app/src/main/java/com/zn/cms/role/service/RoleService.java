package com.zn.cms.role.service;

import com.zn.cms.role.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<RoleDTO> findByName(String roleName);

    List<RoleDTO> findAllByNameIn(List<String> roleNames);

    Optional<RoleDTO> createRoleIfNotFound(String name, List<String> permissionNames);

}
