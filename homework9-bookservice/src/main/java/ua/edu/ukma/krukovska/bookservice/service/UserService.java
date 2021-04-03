package ua.edu.ukma.krukovska.bookservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Response;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Role;
import ua.edu.ukma.krukovska.bookservice.persistence.model.User;
import ua.edu.ukma.krukovska.bookservice.persistence.repository.UserRepository;

import java.util.Collections;
import java.util.LinkedList;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public Response<User> updateUser(User user){
        if (user.getUsername().isBlank()){
            return new Response<>(user, Collections.singletonList("Username can't be empty"));
        }

        if (user.getEmail().isBlank()){
            return new Response<>(user, Collections.singletonList("Email can't be empty"));
        }

        if (user.getPassword().isBlank()){
            return new Response<>(user, Collections.singletonList("Password can't be empty"));
        }

        return new Response<>(userRepository.save(user), new LinkedList<>());

    }

    public Response<User> saveUser(User user) {
        User checkUsername = userRepository.findUserByUsernameIgnoreCase(user.getUsername());
        User checkEmail = userRepository.findUserByEmailIgnoreCase(user.getEmail());

        if (checkUsername != null) {
            return new Response<>(user, Collections.singletonList("Username is already taken"));
        }

        if (checkEmail != null) {
            return new Response<>(user, Collections.singletonList("Mail is already taken"));
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));

        user.setPassword(passwordService.encodePassword(user.getRawPassword()));
        userRepository.save(user);
        return new Response<>(user, new LinkedList<>());
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsernameIgnoreCase(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsernameIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

}