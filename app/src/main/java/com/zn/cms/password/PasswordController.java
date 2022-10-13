package com.zn.cms.password;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.zn.cms.utils.Router.PASSWORD;

@Controller
@AllArgsConstructor
@RequestMapping(PASSWORD)
public class PasswordController {
    private final PasswordServiceImpl passwordService;

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam(value = "token") String token, @RequestParam(value = "password") String password) {
        passwordService.resetPassword(token, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        passwordService.sendForgotPasswordMail(email);
        return ResponseEntity.ok().build();
    }

}
