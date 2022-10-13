package com.zn.cms.password;


import com.zn.cms.email.service.EmailServiceImpl;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import com.zn.cms.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zn.cms.utils.Utils.generateRandomPasswordToken;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;
    private final UserRepository userRepository;

    @Value("${application.domain.name}")
    private String domainName;


    @Override
    public void resetPassword(String token, String password) {
        Optional<User> userOpt = userRepository.findByResetPasswordToken(token);
        if (userOpt.isPresent()) {
            if (userService.updatePassword(token, password)) {
                emailService.sendSimpleMessage(userOpt.get().getEmail(), "Password changed", "Your password has been reset. If it wasn't you please try resetting it again using a stronger password.");
            }
        }
    }

    @Override
    public void sendForgotPasswordMail(String email) {
        String token = generateRandomPasswordToken();
        String resetPasswordLink = "http://" + domainName + "/password/reset/?token=" + token;
        if (userService.updateResetPasswordToken(token, email)) {
            emailService.sendSimpleMessage(email, "Reset password", resetPasswordLink);
        }

    }
}
