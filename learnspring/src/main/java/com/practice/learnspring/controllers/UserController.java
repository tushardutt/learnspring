package com.practice.learnspring.controllers;

import com.practice.learnspring.entities.User;
import com.practice.learnspring.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @GetMapping("/users/{pageNo}/{pageSize}")
    public List<User> getUsers(@PathVariable int pageNo, @PathVariable int pageSize){
        return userService.findPaginatedUser(pageNo,pageSize);
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable Long id)
    {
        return userService.getUser(id);
    }
}
