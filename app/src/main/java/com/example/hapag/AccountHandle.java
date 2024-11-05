package com.example.hapag;

import java.io.Serializable;

public class AccountHandle implements Serializable {
    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public AccountHandle(long userId, String email, String firstName, String lastName, String password) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    // Getters
    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}