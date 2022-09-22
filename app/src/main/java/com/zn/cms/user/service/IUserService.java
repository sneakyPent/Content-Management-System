package com.zn.cms.user.service;

import com.zn.cms.user.dto.UserDTO;

import java.util.Optional;

public interface IUserService {

    Optional<UserDTO> findByUsername(String username);

}
