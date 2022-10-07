package com.abhi.blog.configuration;

import com.abhi.blog.model.User;
import com.abhi.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new MyUserDetails(username);
        Optional<User>  user = userRepository.findByUserName(username);
//        return new MyUserDetails(user);
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found:" + username));
        return user.map(MyUserDetails::new).get();
    }

    public void saveuser(User users) {
        userRepository.save(users);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

//    @Autowired
//    public String getEmailByName1(String name){
//        String email = userRepository.findEmailByName(name);
//        return email;
//    }
}
