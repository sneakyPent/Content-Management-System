package com.zn.cms.user.service;

import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
import com.zn.cms.user.dto.UserDTO;
import com.zn.cms.user.mapper.UserMapper;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
            String email, String firstName, String lastName, String userName, String password, List<String> roleNames) {
        Optional<User> userOpt = userRepository.findByUsername(userName);
        List<Role> roles = roleRepository.findAllByNameIn(roleNames);
        if (!userOpt.isPresent()) {
            return Optional.of(userRepository.save(
                    User.builder()
                            .email(email)
                            .firstName(firstName)
                            .lastName(lastName)
                            .username(userName)
                            .password(passwordEncoder.encode(password))
                            .enabled(true)
                            .roles(roles)
                            .build())).map(userMapper::userToUserDTO);
        }
        return userOpt.map(userMapper::userToUserDTO);
    }


    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserDTO);
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::userToUserDTO);

    }
}
