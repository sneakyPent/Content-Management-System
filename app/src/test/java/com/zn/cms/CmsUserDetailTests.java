package com.zn.cms;

import com.zn.cms.permission.service.PermissionService;
import com.zn.cms.role.service.RoleService;
import com.zn.cms.user.service.CmsUserDetailServiceImpl;
import com.zn.cms.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CmsUserDetailTests {
    @Autowired
    private CmsUserDetailServiceImpl impl;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Value("${application.security.user.admin}")
    private String adminUser;

    @Value("${application.security.user.password}")
    private String password;


    @Test
    void checkGettingAllPermissionFromUser() {


//          Create permissions
        permissionService.createPermissionIfNotFound("DELETE_TESTING", Collections.EMPTY_LIST);
        permissionService.createPermissionIfNotFound("UPDATE_TESTING", Collections.EMPTY_LIST);
        permissionService.createPermissionIfNotFound("WRITE_TESTING1", Collections.EMPTY_LIST);
        permissionService.createPermissionIfNotFound("WRITE_TESTING2", Collections.EMPTY_LIST);
        permissionService.createPermissionIfNotFound("READ_TESTING", Collections.EMPTY_LIST);
        permissionService.appendDependPermissions("DELETE_TESTING", Arrays.asList("WRITE_TESTING2"));
        permissionService.appendDependPermissions("UPDATE_TESTING", Arrays.asList("DELETE_TESTING"));
        permissionService.appendDependPermissions("WRITE_TESTING1", Arrays.asList("UPDATE_TESTING"));
        permissionService.appendDependPermissions("WRITE_TESTING2", Arrays.asList("READ_TESTING"));
//          Create role
        roleService.createRoleIfNotFound("myRole",
                Arrays.asList("READ_TESTING", "WRITE_TESTING1", "UPDATE_TESTING", "DELETE_TESTING", "WRITE_TESTING2"));
//          Create user
        userService.createInitUser("permissionTesting@yahoo.com", "test", "permission",
                "myUsername", "myPassword", Collections.singletonList("myRole"));
        UserDetails userDetails = impl.loadUserByUsername("myUsername");
        List<? extends GrantedAuthority> grAuthList = userDetails.getAuthorities().stream().collect(Collectors.toList());
        List<String> l1 = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : grAuthList) {
            l1.add(grantedAuthority.getAuthority());
        }
        List<String> l2 = Arrays.asList("DELETE_TESTING", "UPDATE_TESTING", "WRITE_TESTING1", "WRITE_TESTING2", "READ_TESTING");

        assertTrue(l1.size() == l2.size() &&
                l1.containsAll(l2) && l2.containsAll(l1));
    }

}
