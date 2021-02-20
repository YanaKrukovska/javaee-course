package com.krukovska.junit.validation;

import com.krukovska.junit.exceptions.ConstraintViolationException;
import com.krukovska.junit.exceptions.LoginExistsException;
import com.krukovska.junit.model.NewUser;
import com.krukovska.junit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserValidatorTest {

    private UserValidator userValidator;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userValidator = new UserValidator(userRepository);
    }

    @Test
    public void userExists() {
        when(userRepository.isLoginExists(eq("existingUserLogin"))).thenReturn(true);
        NewUser newUser = NewUser.builder().login("existingUserLogin").password("password1").fullName("fullName1").build();

        assertThatThrownBy(() -> userValidator.validateNewUser(newUser)).isInstanceOf(LoginExistsException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {"toolongpassword", "a-b-c", ""})
    public void invalidPassword(String password) {
        when(userRepository.isLoginExists(eq("newUserLogin"))).thenReturn(false);
        NewUser newUser = NewUser.builder().login("newUserLogin").password(password).fullName("whatever").build();

        assertThatThrownBy(() -> userValidator.validateNewUser(newUser)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void validateLoginExists() {
        when(userRepository.isLoginExists(eq("existingUserLogin"))).thenReturn(true);
        NewUser newUser = NewUser.builder().login("existingUserLogin").password("password1").fullName("fullName1").build();

        assertThat(userValidator.checkNewUserForErrors(newUser)).contains("Login existingUserLogin already taken");
    }

    @Test
    public void validateLoginNotExists() {
        when(userRepository.isLoginExists(eq("newUserLogin"))).thenReturn(false);
        NewUser newUser = NewUser.builder().login("newUserLogin").password("password1").fullName("fullName1").build();

        assertThat(userValidator.checkNewUserForErrors(newUser)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"toolongpassword", "ab", ""})
    public void checkPasswordInvalidSize(String password) {
        assertThat(userValidator.checkPasswordForErrors(password)).contains("Password has invalid size");
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid", "VaLiD", "VaL1"})
    public void checkPasswordValidSize(String password) {
        assertThat(userValidator.checkPasswordForErrors(password)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"pass", "PASS", "123456"})
    public void checkPasswordMatchesRegex(String password) {
        assertThat(userValidator.checkPasswordForErrors(password)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcde!", "+12345", "      "})
    public void checkPasswordNotMatchesRegex(String password) {
        assertThat(userValidator.checkPasswordForErrors(password)).contains("Password doesn't match regex");
    }

    @ParameterizedTest
    @ValueSource(strings = {"p!", "toolong&symbols", "             "})
    public void checkPasswordNotMatchesRegexInvalidSize(String password) {
        assertThat(userValidator.checkPasswordForErrors(password)).hasSameElementsAs(List.of("Password has invalid size", "Password doesn't match regex"));
    }

    @ParameterizedTest
    @NullSource
    void checkPasswordNull(String password) {
        assertThat(userValidator.checkPasswordForErrors(password)).hasSameElementsAs(List.of("Password is null"));
    }

    @ParameterizedTest
    @NullSource
    void checkUserNull(NewUser newUser) {
        assertThat(userValidator.checkNewUserForErrors(newUser)).hasSameElementsAs(List.of("User is null"));
    }
}

