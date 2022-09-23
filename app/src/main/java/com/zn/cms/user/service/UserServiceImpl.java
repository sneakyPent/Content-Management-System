package com.zn.cms.user.service;

import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
import com.zn.cms.user.dto.UserDTO;
import com.zn.cms.user.mapper.UserMapper;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Value("${application.security.user.admin}")
    private String adminUser;

    @Value("${application.security.user.password}")
    private String password;


    public Optional<UserDTO> createUserIfNotFound(
            String userName, String password, List<String> roleNames) {
        Optional<User> userOpt = userRepository.findByUsername(adminUser);
        List<Role> roles = roleRepository.findAllByNameIn(roleNames);
        if (!userOpt.isPresent()) {
            userRepository.save(
                    User.builder()
                            .email("zaharioudakis@yahoo.com")
                            .firstName("Nikolas")
                            .lastName("Zacharioudakis")
                            .username(adminUser)
                            .password(passwordEncoder.encode(password))
                            .enabled(true)
                            .roles(roles)
                            .build());
        }
        return userOpt.map(userMapper::userToUserDTO);
    }


    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::userToUserDTO);

    }
}
