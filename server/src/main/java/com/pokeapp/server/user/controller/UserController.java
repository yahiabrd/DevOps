package com.pokeapp.server.user.controller;

import com.pokeapp.server.user.model.User;
import com.pokeapp.server.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }
}
