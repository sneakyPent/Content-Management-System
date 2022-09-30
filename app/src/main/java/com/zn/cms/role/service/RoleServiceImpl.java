package com.zn.cms.role.service;

import com.zn.cms.permission.mapper.PermissionMapper;
import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.repository.PermissionRepository;
import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.mapper.RoleMapper;
import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
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
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;


    @Override
    public Page<RoleDTO> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::roleToRoleDTO);
    }

    @Override
    public Optional<RoleDTO> findByName(String roleName) {
        return roleRepository.findByName(roleName).map(roleMapper::roleToRoleDTO);
    }

    @Override
    public Optional<RoleDTO> findByNameOrId(Long id, String name) {
        if (name != null){
            return roleRepository.findByName(name).map(roleMapper::roleToRoleDTO);
        }
        else if(id != null){
            return roleRepository.findById(id).map(roleMapper::roleToRoleDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RoleDTO> findById(Long id) {
        return roleRepository.findById(id).map(roleMapper::roleToRoleDTO);
    }

    @Override
    public List<RoleDTO> findAllByNameIn(List<String> roleNames) {
        return roleRepository.findAllByNameIn(roleNames).stream().map(roleMapper::roleToRoleDTO).collect(Collectors.toList());
    }


    public Optional<RoleDTO> createRoleIfNotFound(String name, List<String> permissionNames) {
        Optional<RoleDTO> role = roleRepository.findByName(name).map(roleMapper::roleToRoleDTO);
        List<Permission> permissions = permissionRepository.findAllByNameIn(permissionNames);
        if (!role.isPresent()) {
            return Optional.of(roleRepository.save(
                    Role.builder()
                            .name(name).permissions(permissions).build())).map(roleMapper::roleToRoleDTO);
        }
        return role;
    }

    public Optional<RoleDTO> updateRole(Long id, RoleDTO newRoleDTO) {

        Optional<Role> oldRoleOpt = roleRepository.findById(id);
        if (oldRoleOpt.isPresent()) {
            Role role = oldRoleOpt.get();
            role.setName(newRoleDTO.getName());
            role.setPermissions(newRoleDTO.getPermissions().stream().map(permissionMapper::permissionDTOToPermission).collect(Collectors.toList()));
            return Optional.of(roleRepository.save(role)).map(roleMapper::roleToRoleDTO);
        }
        return Optional.empty();
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.findById(id).ifPresent(roleRepository::delete);
    }
}
