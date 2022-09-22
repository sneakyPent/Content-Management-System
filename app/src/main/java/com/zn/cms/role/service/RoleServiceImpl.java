package com.zn.cms.role.service;


import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.repository.PermissionRepository;
import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;
    private  final PermissionRepository permissionRepository;

    @PostConstruct
    public void init() {
        String name = "ROLE_ADMIN";
        createRoleIfNotFound(name);
    }


    @Override
    public Optional<RoleDTO> findByName(String roleName) {
        return roleRepository.findByName(roleName).map(RoleDTO::new);
    }


    private Optional<Role> createRoleIfNotFound(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        Optional<Permission> permission = permissionRepository.findByName("READ_PRIVILEGE");
        List<Permission> permissions = new ArrayList<Permission>();
        if (!role.isPresent()) {
            roleRepository.save(
                    Role.builder()
                            .name(name).build());
        }
        return role;
    }
}
