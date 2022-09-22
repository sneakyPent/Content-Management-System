package com.zn.cms.user.service;

import com.zn.cms.role.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CmsUserDetailServiceImpl implements UserDetailsService {

    private final IUserService userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username).map(
                user -> new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
        ).orElseThrow(() -> new UsernameNotFoundException("Email " + username + "was not found"));
    }
}
