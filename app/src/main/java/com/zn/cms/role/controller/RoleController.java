package com.zn.cms.role.controller;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public ResponseEntity<Optional<RoleDTO>> findRole(@PathVariable String name) {
        return ResponseEntity.ok(roleService.findByName(name));
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return roleService.createRoleIfNotFound(roleDTO.getName(),
                roleDTO.getPermissions().stream().
                        map(PermissionDTO::getName).collect(Collectors.toList())).
                map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ResponseEntity<RoleDTO> updateRole(@Valid @PathVariable("id") Long id, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(id, roleDTO).
                map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
