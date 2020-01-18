package com.programmingTraining.rest.webservices.restfulwebservices.daoservice;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void shouldFindAllUser() {
        List<User> allUser = userRepository.findAllUser();
        assertThat(allUser, Is.is(IsNull.notNullValue()));
    }

    @Test
    public void saveUser() {
        User saveUser = userRepository.saveUser(new User(0, "Jose", new Date()));
        assertThat(saveUser.getId(), Is.is(IsNull.notNullValue()));
        assertThat(saveUser.getName(), Is.is("Jose"));
    }

    @Test
    public void findUserById() {
        User userById = userRepository.findUserById(1);
        assertThat(userById.getName(), Is.is("Alvaro"));
        assertThat(userById.getId(), Is.is(1));
    }
}