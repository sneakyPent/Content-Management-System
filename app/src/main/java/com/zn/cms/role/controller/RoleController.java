package com.zn.cms.role.controller;

import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.zn.cms.utils.Router.ROLE;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROLE)
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public ResponseEntity<Page<RoleDTO>> findAllRoles(Pageable pageable) {

        return ResponseEntity.ok(roleService.findAll(pageable));
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoleDTO> modifyRole(@RequestBody RoleDTO newRoleDTO ){
        roleService.modifyRole(newRoleDTO);
//        System.out.println(roleDTO.getName());
//        System.out.println(roleDTO.getPermissions());

        return ResponseEntity.ok(null);
//        return ResponseEntity.created(roleService.createRoleIfNotFound());
    }
}
