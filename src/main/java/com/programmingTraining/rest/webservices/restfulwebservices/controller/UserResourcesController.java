package com.programmingTraining.rest.webservices.restfulwebservices.controller;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import com.programmingTraining.rest.webservices.restfulwebservices.daoservice.UserRepositoryImpl;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.ErrorRetrievingUsersExceptions;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.ErrorSavingUserException;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.PostNotFoundException;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResourcesController {

    @Autowired
    private UserRepositoryImpl userDaoService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUser() {
        List<User> allUsers = userDaoService.findAllUser();
        if (allUsers == null) {
            throw new ErrorRetrievingUsersExceptions("There was an error retrieving all the users");
        }
        return allUsers;
    }

    @GetMapping(path = "/users/{userId}")
    public User retrieveUserById(@PathVariable int userId) {
        return getUserById(userId);
    }

    @DeleteMapping(path = "/users/{userId}")
    public void removeUserById(@PathVariable int userId) {
        if (userDaoService.deleteUserById(userId) == null) {
            throw new UserNotFoundException("User with id: '" + userId + "' was not found.");
        }
    }

    @GetMapping(path = "/users/{userId}/posts")
    public List<String> retrievePostsUserById(@PathVariable int userId) {
        User userById = getUserById(userId);
        return userById.getPostList();
    }

    @GetMapping(path = "/users/{userId}/posts/{postId}")
    public String retrievePostsById(@PathVariable int userId, @PathVariable int postId) {
        String post = getUserById(userId).getPostList().get(postId);
        if (post == null) {
            throw new PostNotFoundException("Post with id: " + postId + " was not found.");
        }
        return post;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody User user) {
        User saveUser = userDaoService.saveUser(user);
        if (saveUser == null) {
            throw new ErrorSavingUserException("There was an error executing your save");
        }
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(saveUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(path = "/users/{userId}/posts")
    public ResponseEntity<Object> createNewUserPost(@Valid @RequestBody String newPost, @PathVariable int userId) {
        User userById = getUserById(userId);
        userById.getPostList().add(newPost);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}/posts")
                .buildAndExpand(userById.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private User getUserById(@PathVariable int userId) {
        User userById = userDaoService.findUserById(userId);
        if (userById == null)
            throw new UserNotFoundException("User with id: '" + userId + "' was not found.");
        return userById;
    }

}
