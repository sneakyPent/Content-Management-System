package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.mapper.PermissionMapper;
import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;


    @Override
    public Page<PermissionDTO> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(permissionMapper::permissionToPermissionDTO);
    }

    @Override
    public Optional<PermissionDTO> findByName(String name) {
        return permissionRepository.findByName(name).map(permissionMapper::permissionToPermissionDTO);
    }

    @Override
    public List<PermissionDTO> findAllByNameIn(List<String> permissionNames) {
        return permissionRepository.findAllByNameIn(permissionNames).stream().map(permissionMapper::permissionToPermissionDTO).collect(Collectors.toList());
    }

    public Optional<PermissionDTO> createPermissionIfNotFound(String name, List<String> dependsOnPermissions) {
        Optional<PermissionDTO> permission = permissionRepository.findByName(name).map(permissionMapper::permissionToPermissionDTO);
        List<Permission> depPermissions = permissionRepository.findAllByNameIn(dependsOnPermissions);
        if (!permission.isPresent()) {
            permissionRepository.save(
                    Permission.builder()
                            .name(name)
                            .dependsOnPermissions(depPermissions).build());
        }
        return permission;
    }

    @Override
    public Optional<PermissionDTO> appendDependPermissions(String name, List<String> givenDependsOnPermissionsName) {
        Optional<Permission> permissionOptional = permissionRepository.findByName(name);
        List<Permission> newDepPermissionList = permissionRepository.findAllByNameIn(givenDependsOnPermissionsName);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            List<Permission> oldDeps = permission.getDependsOnPermissions().stream().collect(Collectors.toList());
            if (oldDeps.addAll(newDepPermissionList)) {
//              Get in only if oldDependsOnPermission list has been changed
                permission.setDependsOnPermissions(oldDeps);
                return Optional.of(permissionRepository.save(permission)).map(permissionMapper::permissionToPermissionDTO);
            }
        }
        return Optional.empty();
    }
}
