package com.symphony.hotelchallenge.web.controller;

import com.symphony.hotelchallenge.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    //dependency injection
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // displays the register page
    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "masterTemplate";
    }

    // if the input passes the if statement registers new user and redirects to login,
    // else brings the user back to the register form with an error message
    @PostMapping
    public String register(@RequestParam String email, @RequestParam String username, @RequestParam String password) {
        if (userService.findByUsername(username) == null &&
                password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}") &&
                userService.findByEmail(email) == null) {
            this.userService.register(email, username, password);
            return "redirect:/login";
        } else {
            String error = "Credentials are either taken or invalid";
            return "redirect:/register?error=" + error;
        }
    }
}
