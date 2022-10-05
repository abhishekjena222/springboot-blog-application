package com.abhi.blog.services;

import com.abhi.blog.model.User;

public interface UserService {
    String getEmailByName(String name);

    void saveuser(User user);
}
