package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Optional<PermissionDTO> findByName(String name);

    List<PermissionDTO> findAllByNameIn(List<String> permissionNames);

    Optional<PermissionDTO> createPermissionIfNotFound(String name);
}
