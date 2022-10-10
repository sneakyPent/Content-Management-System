package com.zn.cms.user.service;

import com.zn.cms.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<UserDTO> findAll(Pageable pageable);

    Optional<UserDTO> findByUsername(String username);

    Optional<UserDTO> createUserIfNotFound(String email, String firstName, String lastName, List<String> roleNames);

    Optional<UserDTO> createInitUser(
            String email, String firstName, String lastName, String userName, String password, List<String> roleNames);

    Optional<UserDTO> updateUser(Long id, UserDTO userDTO);

    boolean deleteUser(Long id);
}
