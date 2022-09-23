package com.zn.cms.role.controller;

import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.zn.cms.utils.Router.ROLE;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROLE)
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<Page<RoleDTO>> getAllRoles(Pageable pageable) {

        return ResponseEntity.ok(roleService.findAll(pageable));
    }

}
