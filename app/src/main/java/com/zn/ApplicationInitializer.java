package com.zn;

import com.zn.cms.permission.service.IPermissionService;
import com.zn.cms.role.service.IRoleService;
import com.zn.cms.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final IPermissionService permissionService;
    private final IRoleService roleService;
    private final IUserService userService;

    @Value("${application.security.user.admin}")
    private String adminUser;

    @Value("${application.security.user.password}")
    private String password;

    @Override
    public void run(String... args) {
        permissionService.createPermissionIfNotFound("READ_PRIVILEGE");
        roleService.createRoleIfNotFound("ROLE_ADMIN", Collections.singletonList("READ_PRIVILEGE"));
        userService.createUserIfNotFound(adminUser, password, Collections.singletonList("ROLE_ADMIN"));

    }
}
