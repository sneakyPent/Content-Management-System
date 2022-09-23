package com.zn.cms.user.mapper;

import com.zn.cms.role.mapper.RoleMapper;
import com.zn.cms.user.dto.UserDTO;
import com.zn.cms.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring",
        uses = {RoleMapper.class
        })
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
