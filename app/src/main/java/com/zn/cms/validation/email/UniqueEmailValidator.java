package com.zn.cms.validation.email;

import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import com.zn.cms.validation.email.UniqueEmail;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    private final UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> unavailableEmails = userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
        return !unavailableEmails.contains(value);
    }
}
