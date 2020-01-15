package com.programmingTraining.rest.webservices.restfulwebservices.daoservice;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {

    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;

    static {
        users.add(new User(1, "Alvaro", new Date()));
        users.add(new User(2, "Maria Laura", new Date()));
        users.add(new User(3, "Titi", new Date()));
    }

    @Override
    public List<User> findAllUser() {
        return users;
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    @Override
    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == (id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User deleteUserById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == (id)) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
