package ua.edu.ukma.krukovska.bookservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Response;
import ua.edu.ukma.krukovska.bookservice.persistence.model.User;
import ua.edu.ukma.krukovska.bookservice.service.PasswordService;
import ua.edu.ukma.krukovska.bookservice.service.UserService;

@Controller
public class AuthenticationController {

    private final UserService userService;
    private final PasswordService passwordService;

    @Autowired
    public AuthenticationController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping("/login-processing")
    public String loginUser(@ModelAttribute User user, Model model) {

        User foundUser = userService.findUserByUsername(user.getUsername());

        if (foundUser == null) {
            model.addAttribute("error", "User doesn't exists");
            return "login";
        }

        if (!passwordService.compareRawAndEncodedPassword(user.getPassword(), (foundUser.getPassword()))) {
            model.addAttribute("error", "Wrong password");
            return "login";
        }

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup-processing")
    public String addUser(@ModelAttribute User user, Model model) {

        Response<User> errorList = userService.saveUser(user);
        if (!errorList.isOkay()) {
            model.addAttribute("error", errorList.getErrors().get(0));
            return "signup";
        }

        return "redirect:/login";
    }

}
