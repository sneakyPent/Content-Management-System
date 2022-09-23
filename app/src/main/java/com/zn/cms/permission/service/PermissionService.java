package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Page<PermissionDTO> findAll(Pageable pageable);

    Optional<PermissionDTO> findByName(String name);

    List<PermissionDTO> findAllByNameIn(List<String> permissionNames);

    Optional<PermissionDTO> createPermissionIfNotFound(String name);
}
