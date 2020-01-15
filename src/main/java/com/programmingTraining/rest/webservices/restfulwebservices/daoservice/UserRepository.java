package com.programmingTraining.rest.webservices.restfulwebservices.daoservice;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;

import java.util.List;

public interface UserRepository {

    public List<User> findAllUser();

    public User saveUser(User user);

    public User findUserById(int id);

    public User deleteUserById(int id);


}
