package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.mapper.PermissionMapper;
import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;


    @Override
    public Optional<PermissionDTO> findByName(String name) {
        return permissionRepository.findByName(name).map(PermissionDTO::new);
    }

    @Override
    public List<PermissionDTO> findAllByNameIn(List<String> permissionNames) {
        return permissionRepository.findAllByNameIn(permissionNames).stream().map(permissionMapper::permissionToPermissionDTO).collect(Collectors.toList());
    }

    public Optional<PermissionDTO> createPermissionIfNotFound(String name) {
        Optional<PermissionDTO> permission = permissionRepository.findByName(name).map(PermissionDTO::new);
        if (!permission.isPresent()) {
            permissionRepository.save(
                    Permission.builder()
                            .name(name).build());
        }
        return permission;
    }

}
