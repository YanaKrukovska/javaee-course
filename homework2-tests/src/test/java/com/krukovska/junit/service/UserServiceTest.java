package com.krukovska.junit.service;

import com.krukovska.junit.exceptions.ConstraintViolationException;
import com.krukovska.junit.exceptions.LoginExistsException;
import com.krukovska.junit.exceptions.UserNotFoundException;
import com.krukovska.junit.model.NewUser;
import com.krukovska.junit.model.User;
import com.krukovska.junit.repository.UserRepository;
import com.krukovska.junit.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @SpyBean
    UserRepository userRepository;

    @SpyBean
    UserValidator userValidator;

    @Test
    public void getExistingUserSuccessfully() {
        assertThat(userService.getUserByLogin("login1")).isNotNull();
        verify(userRepository, times(1)).getUserByLogin(eq("login1"));
    }

    @Test
    public void getNonExistingUser() {
        assertThatThrownBy(() -> userService.getUserByLogin("login0")).isInstanceOf(UserNotFoundException.class);
        verify(userRepository, times(1)).getUserByLogin(eq("login0"));
    }

    @Test
    public void saveNewUserSuccessfully() {
        userService.createNewUser(NewUser.builder().login("cat1").password("pass1").fullName("fullName4").build());
        User createdUser = userRepository.getUserByLogin("cat1");

        verify(userValidator, times(1)).validateNewUser(any(NewUser.class));

        assertAll(
                "new user fields assert",
                () -> assertEquals("cat1", createdUser.getLogin()),
                () -> assertEquals("pass1", createdUser.getPassword()),
                () -> assertEquals("fullName4", createdUser.getFullName())
        );
    }

    @Test
    public void saveExistingUser() {
        NewUser newUser = NewUser.builder().login("login1").password("pass1").fullName("fullName4").build();

        assertThatThrownBy(() -> userService.createNewUser(newUser)).isInstanceOf(LoginExistsException.class);
        verify(userValidator, times(1)).validateNewUser(any(NewUser.class));
        verify(userRepository, never()).saveNewUser(any(NewUser.class));
    }

    @Test
    public void saveNotExistingUserWrongPassword() {
        NewUser newUser = NewUser.builder().login("cat2").password("thispasswordisinvalid").fullName("fullName5").build();

        assertThatThrownBy(() -> userService.createNewUser(newUser)).isInstanceOf(ConstraintViolationException.class);
        verify(userValidator, times(1)).validateNewUser(any(NewUser.class));
        verify(userRepository, never()).saveNewUser(any(NewUser.class));
    }

}
