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
                        getAuthorities(user))
        ).orElseThrow(() -> new UsernameNotFoundException("Email " + username + "was not found"));
        log.info("UserDetails {}", usrDet);
        return usrDet;
    }

    private Set<GrantedAuthority> getAuthorities(UserDTO userDTO){
        List<String> authorityNames = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()) {
            for (PermissionDTO permission : role.getPermissions()) {
                authorityNames.add(permission.getName());
                for (PermissionDTO dependsOnPermission : permission.getDependsOnPermissions()) {
                    authorityNames.add(dependsOnPermission.getName());
                }
            }
        }
        Set<GrantedAuthority> auths =  authorityNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        log.info("authorityNames {}", auths);
        return auths;

    }
}
