package com.zn.cms.user.controller;

import com.zn.cms.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.zn.cms.user.service.UserService;

import org.springframework.data.domain.Pageable;

import static com.zn.cms.utils.Router.USER;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER)
public class UserController {

    private final UserService userService;
    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name or hasAuthority('READ_USER')")
    public ResponseEntity<String> findUser(@PathVariable String username){
        return ResponseEntity.ok("find user with id=" + username);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<String> create(){
        return ResponseEntity.ok("created user!");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<String> modifyUser(@PathVariable String id){
        return ResponseEntity.ok("modify user with id=" + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        return ResponseEntity.ok("delete user with id=" + id);
    }
}
