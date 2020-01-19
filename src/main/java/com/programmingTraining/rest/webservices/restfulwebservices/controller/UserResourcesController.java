package com.programmingTraining.rest.webservices.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import com.programmingTraining.rest.webservices.restfulwebservices.daoservice.UserRepositoryImpl;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.ErrorRetrievingUsersExceptions;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.ErrorSavingUserException;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.PostNotFoundException;
import com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public Resource<User> retrieveUserById(@PathVariable int userId) {
        User userById = getUserById(userId);

        Resource<User> resource = new Resource<>(userById);
        ControllerLinkBuilder controllerLinkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUser());
        resource.add(controllerLinkBuilder.withRel("all-users"));
        return resource;
    }

    @DeleteMapping(path = "/users/{userId}")
    public void removeUserById(@PathVariable int userId) {
        if (userDaoService.deleteUserById(userId) == null) {
            throw new UserNotFoundException("User with id: '" + userId + "' was not found.");
        }
    }

    @GetMapping(path = "/users/{userId}/posts")
    public List<String> retrieveUserPosts(@PathVariable int userId) {
        User userById = getUserById(userId);
        return userById.getPostList();
    }

    @GetMapping(path = "/users/{userId}/posts/{postId}")
    public Resource<String> retrieveUserPostsById(@PathVariable int userId, @PathVariable int postId) {
        if (getUserById(userId).getPostList().size() < userId) {
            throw new PostNotFoundException("Post with id: '" + postId + "' was not found.");
        }
        Resource<String> resource = new Resource<>(getUserById(userId).getPostList().get(postId));
        ControllerLinkBuilder controllerLinkBuilder = linkTo(methodOn(this.getClass()).retrieveUserPosts(userId));
        resource.add(controllerLinkBuilder.withRel("all-posts"));
        return resource;
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

    private MappingJacksonValue controllerFilter(Object Users, String filteredField) {
        SimpleBeanPropertyFilter beanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept(filteredField);
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UsersFilter", beanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(Users);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

}
