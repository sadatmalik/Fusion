package com.sadatmalik.fusion.model;

import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private int userId;

    public User(String firstName, String lastName, String email, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
