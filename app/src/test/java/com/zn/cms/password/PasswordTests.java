package com.zn.cms.password;

import com.zn.cms.password.model.ResetPassword;
import com.zn.cms.password.repository.ResetPasswordRepository;
import com.zn.cms.password.service.PasswordServiceImpl;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;
    @Autowired
    private UserRepository userRepository;

    private void createExpiredResetPassword(String uuid, String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        Instant now = Instant.now();
        Instant tomorrow = now.minus(1, ChronoUnit.DAYS);
        if (userOpt.isPresent()) {
            ResetPassword resetPassword =
                    ResetPassword.builder()
                            .userId(userOpt.get().getId())
                            .uuid(uuid)
                            .expirationDate(tomorrow)
                            .build();
            resetPasswordRepository.save(resetPassword);
        }
    }

    private void createValidResetPassword(String uuid, String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        Instant now = Instant.now();
        Instant tomorrow = now.plus(1, ChronoUnit.DAYS);
        if (userOpt.isPresent()) {
            ResetPassword resetPassword =
                    ResetPassword.builder()
                            .userId(userOpt.get().getId())
                            .uuid(uuid)
                            .expirationDate(tomorrow)
                            .build();
            resetPasswordRepository.save(resetPassword);
        }
    }


    @Test
    void checkExpirationResetPasswordTest() {
        String validUuid = UUID.randomUUID().toString();
        String expiredUuid = UUID.randomUUID().toString();
        String validEmail = "zaharioudakis@yahoo.com";
        String expiredEmail = "contributor@yahoo.com";
        createValidResetPassword(validUuid, validEmail);
        createExpiredResetPassword(expiredUuid, expiredEmail);
        Optional<User> validUserOptional = userRepository.findByEmail(validEmail);
        Optional<User> expiredUserOptional = userRepository.findByEmail(expiredEmail);
        if (validUserOptional.isPresent() && expiredUserOptional.isPresent()) {
            User validUser = validUserOptional.get();
            User expiredUser = expiredUserOptional.get();
            Optional<ResetPassword> validResetPasswordOptional = resetPasswordRepository.findByUserId(validUser.getId());
            Optional<ResetPassword> expiredResetPasswordOptional = resetPasswordRepository.findByUserId(expiredUser.getId());
            if (validResetPasswordOptional.isPresent() && expiredResetPasswordOptional.isPresent()) {
                ResetPassword validResetPassword = validResetPasswordOptional.get();
                ResetPassword expiredResetPassword = expiredResetPasswordOptional.get();

//              check valid reset password
                assert Objects.equals(validUuid, validResetPassword.getUuid());
                assert Objects.equals(validEmail, userRepository.findById(validResetPassword.getUserId()).get().getEmail());
                assert validResetPassword.getExpirationDate().compareTo(Instant.now()) >= 0;

//              check expired reset password
                assert Objects.equals(expiredUuid, expiredResetPassword.getUuid());
                assert Objects.equals(expiredEmail, userRepository.findById(expiredResetPassword.getUserId()).get().getEmail());
                assert expiredResetPassword.getExpirationDate().compareTo(Instant.now()) < 0;
            }

        }
    }
}
