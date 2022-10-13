package com.zn.cms.user.service;

import com.zn.cms.configuration.ApplicationSecurity;
import com.zn.cms.role.mapper.RoleMapper;
import com.zn.cms.role.model.Role;
import com.zn.cms.role.repository.RoleRepository;
import com.zn.cms.role.service.RoleService;
import com.zn.cms.user.dto.UserDTO;
import com.zn.cms.user.mapper.UserMapper;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ApplicationSecurity applicationSecurity;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private String generateRandomUniqueUsername() {
        List<String> unavailableUsernames = userRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
        int n = 5;
        Random randGen = new Random();
        int startNum = (int) Math.pow(10, n - 1);
        int range = (int) (Math.pow(10, n) - startNum + 1);

        int randomNum;
        String newUserName;
        do {

            randomNum = randGen.nextInt(range) + startNum;
            newUserName = "user" + randomNum;
        } while (unavailableUsernames.contains(newUserName));
        return newUserName;

    }

    public Optional<UserDTO> createInitUser(
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

    public Optional<UserDTO> createUserIfNotFound(
            String email, String firstName, String lastName, String userName, List<String> roleNames) {
        List<Role> roles = roleRepository.findAllByNameIn(roleNames);
        String password = applicationSecurity.generatePassword();
        Optional<User> userOpt = userRepository.findByUsername(userName);
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

    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        Optional<User> oldUserOptional = userRepository.findById(id);
        if (oldUserOptional.isPresent()) {
            User oldUser = oldUserOptional.get();
//            oldUser.setUsername(userDTO.getUsername());
            oldUser.setFirstName(userDTO.getFirstName());
            oldUser.setLastName(userDTO.getLastName());
            oldUser.setEmail(userDTO.getEmail());

            List<Role> roles = userDTO.getRoles().stream()
                    .map(roleMapper::roleDTOToRole).collect(Collectors.toList()).stream()
                    .map(role -> roleService.findByNameOrId(role.getId(), role.getName())
                            .map(roleMapper::roleDTOToRole)).collect(Collectors.toList()).stream()
                    .map(Optional::get).collect(Collectors.toList());
            oldUser.setRoles(roles);
            return Optional.of(userRepository.save(oldUser)).map(userMapper::userToUserDTO);
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        Optional<User> user = (userRepository.findById(id));
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean updateResetPasswordToken(String token, String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setResetPasswordToken(token);
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }

    public boolean updatePassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetPasswordToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            user.setResetPasswordToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
