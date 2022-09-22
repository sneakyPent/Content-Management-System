package com.zn.cms.role.service;

import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

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
        if (!role.isPresent()) {
            System.out.println("----------> Role not found");
        }
        return role;
    }
}
