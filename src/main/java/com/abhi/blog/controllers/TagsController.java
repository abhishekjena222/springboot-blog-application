package com.abhi.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abhi.blog.model.Posts;
import com.abhi.blog.model.Tags;
import com.abhi.blog.services.PostsServices;
import com.abhi.blog.services.TagsServices;

@Controller
public class TagsController {
	
	@Autowired
	private TagsServices tagsSerrvices;
	@Autowired
	private PostsServices postsServices;
	
	@RequestMapping("/viewtags")
	public String viewAllTags(Model model) {
		model.addAttribute("allTags",tagsSerrvices.getAllTags());
		return "view_Tags";
	}

}
