package com.zn.cms.user.controller;

import com.zn.cms.role.dto.RoleDTO;
import com.zn.cms.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.zn.cms.user.service.UserService;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

import static com.zn.cms.utils.Router.USER;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER)
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name or hasAuthority('READ_USER')")
    public ResponseEntity<UserDTO> findUser(@PathVariable String username) {
        return userService.findByUsername(username).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUserIfNotFound(userDTO.getEmail(), userDTO.getFirstName(),
                userDTO.getLastName(), userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getRoles().stream().map(RoleDTO::getName).collect(Collectors.toList())).
                map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
