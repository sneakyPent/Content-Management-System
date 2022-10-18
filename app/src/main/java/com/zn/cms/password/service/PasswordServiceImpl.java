package com.zn.cms.password.service;


import com.zn.cms.email.service.EmailServiceImpl;
import com.zn.cms.password.model.ResetPassword;
import com.zn.cms.password.repository.ResetPasswordRepository;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import com.zn.cms.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final EmailServiceImpl emailService;
    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.domain.name}")
    private String domainName;

    public boolean isExpired(ResetPassword resetPassword) {
        return resetPassword.getExpirationDate().compareTo(Instant.now()) < 0;
    }

    @Override
    public void resetPassword(String uuid, String newPassword) throws ExpiredUUID {
        Optional<ResetPassword> resetPasswordOptional = resetPasswordRepository.findByUuid(uuid);
        if (resetPasswordOptional.isPresent()) {
            ResetPassword resetPassword = resetPasswordOptional.get();
            if (!isExpired(resetPassword)) {
                Optional<User> userOptional = userRepository.findById(resetPassword.getUserId());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    String encodedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encodedPassword);
                    userRepository.save(user);
                    emailService.sendSimpleMessage(user.getEmail(), "Password changed",
                            "Your password has been reset. If it wasn't you please try resetting it again using a stronger password.");
                    resetPasswordRepository.delete(resetPassword);
                }
            }
            else {
                resetPasswordRepository.delete(resetPassword);
                throw new ExpiredUUID("This link has been expired");

            }
        }
    }

    @Override
    public void sendForgotPasswordMail(String email) {
        String uuid = UUID.randomUUID().toString();
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            String resetPasswordLink = "http://" + domainName + "/password/reset/?uuid=" + uuid;
            emailService.sendSimpleMessage(email, "Reset password", resetPasswordLink);
            ResetPassword resetPassword =
                    ResetPassword.builder()
                            .userId(userOpt.get().getId())
                            .uuid(uuid)
                            .expirationDate(Instant.now().plus(1, ChronoUnit.DAYS))
                            .build();
            resetPasswordRepository.save(resetPassword);
        }
    }
}
