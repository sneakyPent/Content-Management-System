package com.zn.cms.permission.service;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {
    private final PermissionRepository permissionRepository;

    @PostConstruct
    public void init() {
        String permission = "READ_PRIVILEGE";
        createPermissionIfNotFound(permission);
    }

    @Override
    public Optional<PermissionDTO> findByName(String name) {
        return permissionRepository.findByName(name).map(PermissionDTO::new);
    }

    public Optional<Permission> createPermissionIfNotFound(String name) {
        Optional<Permission> permission = permissionRepository.findByName(name);
        if (!permission.isPresent()) {
            permissionRepository.save(
                    Permission.builder()
                            .name(name).build());
        }
        return permission;
    }

}
