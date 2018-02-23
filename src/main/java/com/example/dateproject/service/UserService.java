package com.example.dateproject.service;

import com.example.dateproject.models.User;

public interface UserService {

    public User findUserByEmail(String email);
    public void saveUser(User user);

}
