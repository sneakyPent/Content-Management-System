package com.zn.cms.user.service;

import com.zn.cms.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> findByUsername(String username);

    Optional<UserDTO> createUserIfNotFound(String userName, String password, List<String> roleNames);

}
