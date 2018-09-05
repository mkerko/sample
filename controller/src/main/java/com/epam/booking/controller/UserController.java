package com.epam.booking.controller;

import com.epam.booking.IUserService;
import com.epam.booking.dto.UserSocialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users/register")
    public void registerUser(@RequestBody UserSocialDTO userSocialDto){
        userService.registerUser(userSocialDto);
    }

    @PostMapping(value = "/checkToken")
    public String checkToken(@RequestParam(name = "token") String idToken) {
        String refreshToken = userService.getRefreshToken(idToken);
        return "{\"refresh_token\": \"" + refreshToken + "\"}";
    }
}
