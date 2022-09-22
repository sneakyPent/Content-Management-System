package com.zn.cms.user.service;

import com.zn.cms.user.dto.UserDTO;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${application.security.user.admin}")
    private String adminUser;

    @Value("${application.security.user.password}")
    private String password;


    @PostConstruct
    public void init() throws IOException {
        Optional<User> userOpt = userRepository.findByUsername(adminUser);
        if (!userOpt.isPresent()) {
            userRepository.save(
                    User.builder()
                            .email("zaharioudakis@yahoo.com")
                            .firstName("Nikolas")
                            .lastName("Zacharioudakis")
                            .username(adminUser)
                            .password(passwordEncoder.encode(password))
                            .enabled(true)
                            .roles(new ArrayList<>())
                            .build());
        }
    }


    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDTO::new);

    }
}
