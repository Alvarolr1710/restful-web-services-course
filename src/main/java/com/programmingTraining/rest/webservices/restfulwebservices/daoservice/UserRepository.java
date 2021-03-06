package com.programmingTraining.rest.webservices.restfulwebservices.daoservice;

import com.programmingTraining.rest.webservices.restfulwebservices.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
