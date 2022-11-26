package com.khan.pagination.model;

public class UserModel {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String avatar;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserModel(String firstName, String lastName, String email, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
    }
}

