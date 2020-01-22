package com.programmingTraining.rest.webservices.restfulwebservices.controller;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.Post;
import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import com.programmingTraining.rest.webservices.restfulwebservices.daoservice.PostRepository;
import com.programmingTraining.rest.webservices.restfulwebservices.daoservice.UserRepository;
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
import java.util.Optional;

@RestController
public class UserResourcesController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @GetMapping(path = "/users")
    public List<User> retrieveAllUser() {
        List<User> allUsers = userRepository.findAll();
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
        userRepository.deleteById(userId);
    }

    @GetMapping(path = "/users/{userId}/posts")
    public List<Post> retrieveUserPosts(@PathVariable int userId) {
        User userById = getUserById(userId);
        return userById.getPostList();
    }

    @GetMapping(path = "/users/{userId}/posts/{postId}")
    public Post retrieveUserPostsById(@PathVariable int userId, @PathVariable int postId) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(postId);
        if (!post.isPresent())
            throw new PostNotFoundException("Post with id: '" + postId + "' was not found.");

        return post.get();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody User user) {
        User saveUser = userRepository.saveAndFlush(user);
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
    public ResponseEntity<Object> createNewUserPost(@Valid @RequestBody Post post, @PathVariable int userId) {
        User userById = saveUserPost(post, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}/posts")
                .buildAndExpand(userById.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private User saveUserPost(@Valid Post post, int userId) {
        User userById = getUserById(userId);
        post.setUser(userById);
        postRepository.save(post);
        return userById;
    }

    private User getUserById(int userId) {
        Optional<User> userById = userRepository.findById(userId);
        if (!userById.isPresent())
            throw new UserNotFoundException("User with id: '" + userId + "' was not found.");
        return userById.get();
    }


}
