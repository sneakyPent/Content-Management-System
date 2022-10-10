package com.zn.cms.user.dto;

import com.zn.cms.role.dto.RoleDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private String password;
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    private Collection<RoleDTO> roles;

}
