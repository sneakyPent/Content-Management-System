package com.zn.cms.user.controller;

import com.zn.cms.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findUser(@PathVariable String id){
        return ResponseEntity.ok("find user with id=" + id);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(){
        return ResponseEntity.ok("created user!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifyUser(@PathVariable String id){
        return ResponseEntity.ok("modify user with id=" + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        return ResponseEntity.ok("delete user with id=" + id);
    }
}
