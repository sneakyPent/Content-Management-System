package com.zn.cms.user.dto;

import com.zn.cms.role.dto.RoleDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.zn.cms.validation.email.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.zn.cms.validation.username.Username;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private Long id;
    @Username(message = "Username is not available")
    private String username;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private String password;
    @Email
    @NotBlank(message = "Email is mandatory")
    @UniqueEmail(message = "Email is not available")
    private String email;
    private Collection<RoleDTO> roles;

}
