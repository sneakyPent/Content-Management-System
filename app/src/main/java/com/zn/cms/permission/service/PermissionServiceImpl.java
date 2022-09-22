package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService{
    private final PermissionRepository permissionRepository;

    @Override
    public Optional<PermissionDTO> findByName(String name) {
        return permissionRepository.findByName(name).map(PermissionDTO::new);
    }
}
