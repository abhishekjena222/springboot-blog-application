package com.abhi.blog.controllers;

import com.abhi.blog.configuration.MyUserDetails;
import com.abhi.blog.configuration.MyUserDetailsService;
import com.abhi.blog.model.User;
import com.abhi.blog.repositories.UserRepository;
import com.abhi.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abhi.blog.model.Comments;
import com.abhi.blog.model.Posts;
import com.abhi.blog.services.CommentsServices;
import com.abhi.blog.services.PostsServices;

@Controller
public class CommentsController {
	
	@Autowired
	private CommentsServices commentsServices;
	@Autowired
	private PostsServices postsServices;


	@PostMapping("/savecomment/{id}")
	public String getCommentsByPostsId(@PathVariable(value = "id") Long id, @ModelAttribute("comments") Comments comment, User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		String eemail = user.getEmail();
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
		System.out.println(eemail);
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
		comment.setName(currentPrincipalName);
		Posts post = postsServices.getPostsById(id);
		comment.setPost(post);
		commentsServices.saveComments(comment);
		post.getComments().add(comment);
		return "redirect:/post/"+id;	
	}
	
	@RequestMapping("/viewcomments")
	public String viewAllComments(Model model) {
		model.addAttribute("allComments",commentsServices.getAllComments());
		return "view_Comments";	
	}
	
	@RequestMapping("/deleteComment/{id1}/{id2}")
	public String deleteComment(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2) {
		this.commentsServices.deleteCommentById(id1);
		return "redirect:/post/"+id2;	
	}
	@RequestMapping("/editComment/{id1}/{id2}")
	public String editComment(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2, Model model) {
		Comments comments = commentsServices.getCommentById(id1);
		model.addAttribute("comment",comments);
//		model.addAttribute("posts",id2);
		return "edit_Comment";
		
	}

}
