package com.zn.cms.validation.username;

import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> unavailableUsernames = userRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
        return !unavailableUsernames.contains(value);
    }
}
