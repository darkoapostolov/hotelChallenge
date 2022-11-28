package com.symphony.hotelchallenge.web.controller;

import com.symphony.hotelchallenge.model.User;
import com.symphony.hotelchallenge.model.exceptions.InvalidUserCredentialsException;
import com.symphony.hotelchallenge.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    // dependency injection
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // displays login page, first page the user sees
    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "masterTemplate";
    }

    // logs user in or redirects him back to the login form
    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user;
        try {
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}
