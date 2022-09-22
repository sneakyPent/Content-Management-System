package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;

import java.util.Optional;

public interface IPermissionService {

    Optional<PermissionDTO> findByName(String name);
}
