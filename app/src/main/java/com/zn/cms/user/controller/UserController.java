package com.zn.cms.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zn.cms.utils.Router.USER;

@RestController
@RequestMapping(USER)
public class UserController {

    @GetMapping("/")
    public ResponseEntity<String> findAll(){
        return ResponseEntity.ok("Getting all users!");
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
