package com.programmingTraining.rest.webservices.restfulwebservices.classes;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "All details about the user")
public class User {

    private int id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name should only contains letters")
    @ApiModelProperty(notes = "name should only contain letters")
    private String name;

    @Past
    @ApiModelProperty(notes = "Birthday should be in the past")
    private Date birthDate;
    private List<String> postList;

    public List<String> getPostList() {
        return postList;
    }

    public void setPostList(List<String> postList) {
        this.postList = postList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public User(int id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.postList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", postList=" + postList +
                '}';
    }
}
