package com.programmingTraining.rest.webservices.restfulwebservices.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserResources {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUser() {
        return userDaoService.findAllUser();
    }

    @GetMapping(path = "/users/{userId}")
    public User retrieveUserById(@PathVariable int userId) {
        return userDaoService.findUserById(userId);
    }


}