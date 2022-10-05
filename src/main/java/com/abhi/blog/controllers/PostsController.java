package com.abhi.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import com.abhi.blog.configuration.MyUserDetails;
import com.abhi.blog.model.User;
import com.abhi.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.abhi.blog.model.Comments;
import com.abhi.blog.model.Posts;
import com.abhi.blog.model.Tags;
import com.abhi.blog.services.PostsServices;
import com.abhi.blog.services.TagsServices;

@Controller
public class PostsController {
	
	@Autowired
	private PostsServices postsServices;
	
	@Autowired
	private TagsServices tagsServices;



	@RequestMapping("/")
	public String viewHomePage(Model model, String keyword) {
		int pageSize = 4;
		if(keyword == null || keyword == ""){
			return findPaginated(1, "createdAt", "asc", keyword, model);
		}else {

			List<Posts> listPosts = postsServices.getAllPostsByKey(keyword);
			model.addAttribute("listPosts", listPosts);
			return "index";
		}
	}
	
	@RequestMapping("/newpost")
	public String newPost(Model model) {
		//create model attribute to bind data
		Posts posts = new Posts();
		model.addAttribute("posts",posts);
		return "new_Post";
	}
	
	@PostMapping("/savepost")
	public String savePost(@ModelAttribute("posts") Posts posts, @RequestParam("tagsStr") String tagStri, Tags tags) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		posts.setAuthor(currentPrincipalName);
		posts.setIsPublished(true);

		int endIndex = posts.getContent().length() > 250?250:posts.getContent().length();

		String excerpt = posts.getContent().substring(0,endIndex);
		posts.setExcerpt(excerpt);

		String tagStr[] = tagStri.split(" ");
		List<Tags> taglist = new ArrayList<>();
		
		for(int i =0; i < tagStr.length; i++) {

			Tags presentTag = new Tags();
			presentTag = tagsServices.getTagsByName(tagStr[i]);
			if (presentTag == null){
				Tags newTag = new Tags();
				newTag.setName(tagStr[i]);
				tagsServices.saveTags(newTag);
				taglist.add(newTag);
			}
			else {
				taglist.add(presentTag);
			}
			
		}
		posts.setTags(taglist);
		postsServices.savePosts(posts);

		return "redirect:/";
	}
	
	@RequestMapping("/post/{id}")
	public String viewPost(@PathVariable(value = "id") Long id, Model model, Comments comment, Tags tags, User user) {
		//find the post by id
		Posts posts = postsServices.getPostsById(id);
			model.addAttribute("users", user);
			model.addAttribute("posts", posts);
			model.addAttribute("comments", comment);
			model.addAttribute("tags",tags);
			return "post";				
	}
	
	@RequestMapping("/newpost/{id}")
	public String updatePost(@PathVariable(value="id") Long id, Model model,Tags tags) {
		Posts posts = postsServices.getPostsById(id);
		model.addAttribute("posts",posts);
		return "update_Post";
	}
	
	@RequestMapping("/deletePost/{id}")
	public String deletePost(@PathVariable(value = "id") Long id, Model model) {
		this.postsServices.deletePostsById(id);
		return "redirect:/";
	}
	
	@RequestMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value="pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
								String keyword,
								Model model) {
		int pageSize = 4;
		Page<Posts> page = postsServices.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Posts> listPosts;
		listPosts= page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listPosts",listPosts);
		model.addAttribute("keyword",keyword);
		
		return "index";
		
	}
	
}















