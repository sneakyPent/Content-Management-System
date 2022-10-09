package com.zn.cms.user.service;

import com.zn.cms.permission.dto.PermissionDTO;
import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class CmsUserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usrDet = userService.findByUsername(username).map(
                user -> new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(),
                        getAuthorities(user, new ArrayList<>()))
        ).orElseThrow(() -> new UsernameNotFoundException("Email " + username + "was not found"));
        log.info("UserDetails {}", usrDet);
        return usrDet;
    }

    //    TODO: Before add check if exists
    private void getAuthoritiesFromPermissions(PermissionDTO permissionDTO, List<String> authorityNames) {
        if (!authorityNames.contains(permissionDTO.getName())) {
            if (!permissionDTO.getDependsOnPermissions().isEmpty()) {
                for (PermissionDTO dependsOnPermission : permissionDTO.getDependsOnPermissions()) {
                    //                authorityNames = getAuthoritiesFromPermissions(dependsOnPermission, authorityNames);
                    getAuthoritiesFromPermissions(dependsOnPermission, authorityNames);
                    //                authorityNames.add(dependsOnPermission.getName());
                }
            }
            authorityNames.add(permissionDTO.getName());
        }
    }

    private Set<GrantedAuthority> getAuthorities(UserDTO userDTO, List<String> authorityNames) {
        for (RoleDTO role : userDTO.getRoles()) {
            for (PermissionDTO permission : role.getPermissions()) {
                getAuthoritiesFromPermissions(permission, authorityNames);
            }
        }
        Set<GrantedAuthority> auths = authorityNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        log.info("authorityNames {}", auths);
        return auths;

    }
}
