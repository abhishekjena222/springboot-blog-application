package com.abhi.blog.services;

import com.abhi.blog.model.User;
import com.abhi.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getEmailByName(String name) {

        String email = String.valueOf(userRepository.findEmailByName(name));
        return email;
    }

    @Override
    public void saveuser(User user) {
    userRepository.save(user);
    }
}
