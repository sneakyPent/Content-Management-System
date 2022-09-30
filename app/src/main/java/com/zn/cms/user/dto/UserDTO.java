package com.zn.cms.user.dto;

import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.role.model.Role;
import com.zn.cms.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Collection<RoleDTO> roles;

}
