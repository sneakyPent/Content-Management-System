package com.zn.cms.role.service;

import com.zn.cms.role.dto.RoleDTO;

import java.util.Optional;

public interface IRoleService {

    Optional<RoleDTO> findByName(String roleName);

}
