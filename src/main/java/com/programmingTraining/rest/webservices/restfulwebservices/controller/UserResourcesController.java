package com.programmingTraining.rest.webservices.restfulwebservices.controller;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import com.programmingTraining.rest.webservices.restfulwebservices.daoservice.UserDaoServiceImpl;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandler.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResourcesController {

    @Autowired
    private UserDaoServiceImpl userDaoService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUser() {
        return userDaoService.findAllUser();
    }

    @GetMapping(path = "/users/{userId}")
    public User retrieveUserById(@PathVariable int userId) {
        User userById = userDaoService.findUserById(userId);
        if (userById == null)
            throw new UserNotFoundException("User with id: \"" + userId + "\" was not found.");
        return userById;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> createNewUser(@RequestBody User user) {
        User saveUser = userDaoService.saveUser(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(saveUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
