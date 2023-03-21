package com.youtube.jwt.controller;

import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class UserController {



    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{userName}")
    public User getUserById(@PathVariable String userName) {
        return userService.getUserById(userName);
    }

    @RolesAllowed("Admin")
    @PutMapping("/updateUser/{userName}")
    public User updateUser(@PathVariable String userName, @RequestBody User user) {
        return userService.updateUser(userName, user);
    }

    @RolesAllowed("Admin")
    @DeleteMapping("/delete/{userName}")
    public String deleteUser(@PathVariable String userName) {
        userService.deleteUser(userName);
        return "user deleted :" + userName;
    }







    @GetMapping({"/forAll"})
    public String forAll(){
        return "This URL is accessible to everyone";
    }


    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('ROLE_Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('ROLE_User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }







}

