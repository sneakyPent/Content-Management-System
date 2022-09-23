package com.zn;

import com.zn.cms.permission.service.PermissionService;
import com.zn.cms.role.service.RoleService;
import com.zn.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final UserService userService;

    @Value("${application.security.user.admin}")
    private String adminUser;

    @Value("${application.security.user.password}")
    private String password;

    @Override
    public void run(String... args) {
        permissionService.createPermissionIfNotFound("READ_PRIVILEGE");
        permissionService.createPermissionIfNotFound("READ_ROLE");
        permissionService.createPermissionIfNotFound("WRITE_ROLE");
        permissionService.createPermissionIfNotFound("UPDATE_ROLE");
        permissionService.createPermissionIfNotFound("DELETE_ROLE");
        permissionService.createPermissionIfNotFound("READ_USER");
        permissionService.createPermissionIfNotFound("WRITE_USER");
        permissionService.createPermissionIfNotFound("UPDATE_USER");
        permissionService.createPermissionIfNotFound("DELETE_USER");

        roleService.createRoleIfNotFound("ROLE_ADMIN", Arrays.asList("READ_PRIVILEGE","READ_USER","WRITE_USER","UPDATE_USER","DELETE_USER"));
        roleService.createRoleIfNotFound("ROLE_CONTRIBUTOR", Collections.singletonList("READ_PRIVILEGE"));
        roleService.createRoleIfNotFound("ROLE_DESIGN", Collections.singletonList("READ_PRIVILEGE"));
        userService.createUserIfNotFound(adminUser, password, Collections.singletonList("ROLE_ADMIN"));

    }
}
