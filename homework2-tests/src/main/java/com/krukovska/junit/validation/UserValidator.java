package com.krukovska.junit.validation;

import com.krukovska.junit.exceptions.ConstraintViolationException;
import com.krukovska.junit.exceptions.LoginExistsException;
import com.krukovska.junit.model.NewUser;
import com.krukovska.junit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateNewUser(final NewUser newUser) {
        if (userRepository.isLoginExists(newUser.getLogin())) {
            throw new LoginExistsException(newUser.getLogin());
        }

        validatePassword(newUser.getPassword());
    }

    private void validatePassword(final String password) {
        final List<String> errors = new ArrayList<>();
        if (password.length() < 3 || password.length() > 7) {
            errors.add("Password has invalid size");
        }

        if (!password.matches("[a-zA-Z0-9]+")) {
            errors.add("Password doesn't match regex");
        }

        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }

    // Existing solution works but this implementation provides more testing opportunities
    public List<String> checkNewUserForErrors(NewUser newUser) {
        List<String> errors = new LinkedList<>();

        if (newUser == null) {
            errors.add("User is null");
            return errors;
        }

        if (userRepository.isLoginExists(newUser.getLogin())) {
            errors.add((String.format("Login %s already taken", newUser.getLogin())));
        }

        return errors;
    }

    public List<String> checkPasswordForErrors(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null) {
            errors.add("Password is null");
            return errors;
        }

        if (password.length() < 3 || password.length() > 7) {
            errors.add("Password has invalid size");
        }

        if (!password.matches("[a-zA-Z0-9]+")) {
            errors.add("Password doesn't match regex");
        }

        return errors;
    }

}
