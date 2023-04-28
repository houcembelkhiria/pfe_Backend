package com.youtube.jwt.service;

import com.youtube.jwt.dao.RoleDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("14772869");
        adminUser.setUserPassword(getEncodedPassword("houcem666"));
        adminUser.setUserFirstName("houcem");
        adminUser.setUserLastName("belkhiria");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);


    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("/user/{userName}")
    public User getUserById(@PathVariable("userName") String userName) {
        return userDao.findById(userName).orElseThrow(() -> new RuntimeException("User not found with userName " + userName));
    }


    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDao.save(user);
    }
    @PutMapping("/updateUser/{userName}")
    public User updateUser(@PathVariable("userName") String userName, @RequestBody User user) {
        User existingUser = userDao.findById(userName).orElseThrow(() -> new RuntimeException("User not found with id " + userName));
        existingUser.setUserName(user.getUserName());
        existingUser.setUserFirstName(user.getUserFirstName());
        existingUser.setUserLastName(user.getUserLastName());
        existingUser.setUserPassword(getEncodedPassword(user.getUserPassword()));
        existingUser.setRole(user.getRole()); // update role field
        return userDao.save(existingUser);}
/*
        Role role = new Role();
        role.setRoleName(role.getRoleName());
        role.setRoleDescription(role.getRoleDescription());
        roleDao.save(role);

        Role role1 = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role1);
        existingUser.setRole(userRoles); */




    @DeleteMapping("/delete/{userName}")
    public String deleteUser(@PathVariable("userName") String userName) {
        userDao.deleteById(userName);
        return  "deleted with id : " + userName;

    }




    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
//        User user = new User();
//        user.setUserName("hcm");
//        user.setUserPassword(getEncodedPassword("hcm123"));
//        user.setUserFirstName("hcm");
//        user.setUserLastName("blk");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);