package com.zn.cms.permission.controller;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.permission.service.PermissionService;
import com.zn.cms.permission.service.PermissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
