package com.zn.cms.role.service;

import com.zn.cms.role.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Page<RoleDTO> findAll(Pageable pageable);

    Optional<RoleDTO> findByName(String roleName);

    List<RoleDTO> findAllByNameIn(List<String> roleNames);

    Optional<RoleDTO> createRoleIfNotFound(String name, List<String> permissionNames);

}
