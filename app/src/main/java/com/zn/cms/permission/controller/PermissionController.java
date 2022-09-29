package com.zn.cms.permission.controller;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

import static com.zn.cms.utils.Router.PERMISSION;

@RestController
@RequestMapping(PERMISSION)
@RequiredArgsConstructor
public class PermissionController {


    public final PermissionService permissionService;

    @GetMapping("/")
    public ResponseEntity<Page<PermissionDTO>> getAllPermissions(Pageable pageable) {

        return ResponseEntity.ok(permissionService.findAll(pageable));
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public ResponseEntity<Optional<PermissionDTO>> getPermission(@PathVariable String name) {

        return ResponseEntity.ok(permissionService.findByName(name));
    }

}
