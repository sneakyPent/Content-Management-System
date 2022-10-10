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
    @Value("${application.security.user.contributor}")
    private String contributorUser;
    @Value("${application.security.user.designer}")
    private String designerUser;

    @Value("${application.security.user.password}")
    private String password;

    @Override
    public void run(String... args) {
        permissionService.createPermissionIfNotFound("READ_PERMISSION", Arrays.asList());
        permissionService.createPermissionIfNotFound("READ_ROLE", Arrays.asList());
        permissionService.createPermissionIfNotFound("CREATE_ROLE", Arrays.asList("READ_ROLE"));
        permissionService.createPermissionIfNotFound("DELETE_ROLE", Arrays.asList("READ_ROLE"));
        permissionService.createPermissionIfNotFound("UPDATE_ROLE", Arrays.asList("READ_ROLE"));
        permissionService.createPermissionIfNotFound("READ_USER", Arrays.asList());
        permissionService.createPermissionIfNotFound("CREATE_USER", Arrays.asList("READ_USER"));
        permissionService.createPermissionIfNotFound("UPDATE_USER", Arrays.asList("READ_USER"));
        permissionService.createPermissionIfNotFound("DELETE_USER", Arrays.asList("READ_USER"));

        roleService.createRoleIfNotFound("ROLE_ADMIN", Arrays.asList("READ_PERMISSION","CREATE_ROLE","DELETE_ROLE",
                "UPDATE_ROLE","CREATE_USER","UPDATE_USER","DELETE_USER"));
        roleService.createRoleIfNotFound("ROLE_CONTRIBUTOR", Arrays.asList("UPDATE_ROLE","UPDATE_USER"));
        roleService.createRoleIfNotFound("ROLE_DESIGN", Collections.singletonList("READ_USER"));

        userService.createInitUser("zaharioudakis@yahoo.com", "Nikolas", "Zacharioudakis",
                adminUser, password, Collections.singletonList("ROLE_ADMIN"));
        userService.createInitUser("contributor@yahoo.com", "Vasilis", "Zacharioudakis",
                contributorUser, password, Collections.singletonList("ROLE_CONTRIBUTOR"));
        userService.createInitUser("designer@yahoo.com", "Antonis", "Zacharioudakis",
                designerUser, password, Collections.singletonList("ROLE_DESIGN"));

    }
}
