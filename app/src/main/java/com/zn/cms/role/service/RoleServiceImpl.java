package com.zn.cms.role.service;

import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.repository.PermissionRepository;
import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.mapper.RoleMapper;
import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;


    @Override
    public Optional<RoleDTO> findByName(String roleName) {
        return roleRepository.findByName(roleName).map(RoleDTO::new);
    }

    @Override
    public List<RoleDTO> findAllByNameIn(List<String> roleNames) {
        return roleRepository.findAllByNameIn(roleNames).stream().map(roleMapper::roleToRoleDTO).collect(Collectors.toList());
    }


    public Optional<RoleDTO> createRoleIfNotFound(String name, List<String> permissionNames) {
        Optional<RoleDTO> role = roleRepository.findByName(name).map(RoleDTO::new);
        List<Permission> permissions = permissionRepository.findAllByNameIn(permissionNames);
        if (!role.isPresent()) {
            roleRepository.save(
                    Role.builder()
                            .name(name).permissions(permissions).build());
        }
        return role;
    }
}
