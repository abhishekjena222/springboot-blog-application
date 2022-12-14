package com.abhi.blog.controllers;

import com.abhi.blog.configuration.MyUserDetailsService;
import com.abhi.blog.model.User;
import com.abhi.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

//    @RequestMapping("/login")
//    public String login(){
//        return "login";
//    }

    @RequestMapping("/register")
    public String signup(Model model){
        User user = new User();
        model.addAttribute("users", user);
        return "signup";
    }

    @RequestMapping("/saveuserdata")
    public String saveUserData(@ModelAttribute("users") User users){
        users.setRoles("ROLE_USER");
        users.setActive(true);
        myUserDetailsService.saveuser(users);
        return "redirect:/login";
    }

    @RequestMapping("/adminfield")
    public String signupadmin(Model model){
        User user = new User();
        model.addAttribute("users", user);
        return "admin_Signup";
    }

    @RequestMapping("/saveadmindata")
    public String saveAdminData(@ModelAttribute("users") User users){
        users.setRoles("ROLE_ADMIN");
        users.setActive(true);
        myUserDetailsService.saveuser(users);
        return "redirect:/login";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        List<User> listUser = myUserDetailsService.getAllUser();

        model.addAttribute("listUser",listUser);
        return "admin_index";
    }
}
