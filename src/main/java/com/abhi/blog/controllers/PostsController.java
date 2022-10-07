package com.abhi.blog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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



	@RequestMapping(value = "/")
	public String viewHomePage(Model model, String keyword) {
		int pageSize = 4;
//		String[] abc =authorName.split(",");
//		String[] abcd = tagss.split(",");
//		System.out.println("-------------------------------------");
//		System.out.println(authorName);
//		System.out.println(abc);
//		System.out.println(tagss);
//		System.out.println(abcd);
//		System.out.println("-------------------------------------");
		if(keyword == null || keyword == ""){
			return findPaginated(1, "createdAt", "asc", keyword,model);
		}else {

			List<Posts> listPosts = postsServices.getAllPostsByKey(keyword);
			Set<String> authorsList = postsServices.getAllAuthors();
			List<Tags> tags = tagsServices.getAllTags();
			model.addAttribute("listPosts", listPosts);
			model.addAttribute("authorsList",authorsList);
			model.addAttribute("tags",tags);

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
	public String savePost(@ModelAttribute("posts") Posts posts, @RequestParam("tagsStr") String tagStri,
						   @RequestParam(value = "ispublished",defaultValue = "false") boolean ispublished, Tags tags) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		posts.setAuthor(currentPrincipalName);
		posts.setIsPublished(ispublished);

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
	@RequestMapping("/draft")
	public String draft(Model model){
		List<Posts> draftpost = postsServices.getAllFalsePosts(false);
		List<Tags> tags = tagsServices.getAllTags();
		model.addAttribute("tags",tags);
		model.addAttribute("draftpost",draftpost);
		return "draft";
	}
	
	@RequestMapping("/deletePost/{id}")
	public String deletePost(@PathVariable(value = "id") Long id, Model model) {
		this.postsServices.deletePostsById(id);
		return "redirect:/";
	}
	
	@RequestMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value="pageNo") int pageNo,
								@RequestParam(value = "sortField", required = false) String sortField,
								@RequestParam(value = "sortDir", required = false) String sortDir,

								String keyword,
								Model model) {
		int pageSize = 4;
		Page<Posts> page = postsServices.findPaginated(pageNo, pageSize, sortField, sortDir);
		Set<String> authorsList = postsServices.getAllAuthors();
		List<Tags> tags = tagsServices.getAllTags();
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
		model.addAttribute("authorsList",authorsList);
		model.addAttribute("tags",tags);
		
		return "index";
		
	}

	@RequestMapping("/filter")
	public String filter(@RequestParam(value = "authorName" ,required = false) String author,
						 @RequestParam(value = "tagss",required = false) String tagss,
						 Model model){

		System.out.println("--------------------------------------------------------");
		System.out.println(author);
		System.out.println(tagss);
		System.out.println("--------------------------------------------------------");


		List<Posts> listPosts;
		Set<String> authorsList = postsServices.getAllAuthors();
		List<Tags> tags = tagsServices.getAllTags();
		 if (author==null && tagss==null) {
			return "redirect:/";
		}
		if(author==null){
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("with tagss");
			String[] tagss1 = tagss.split(",");
			listPosts = postsServices.getAllPostsByTags(tagss1);
			model.addAttribute("listPosts", listPosts);
			model.addAttribute("authorsList", authorsList);
			model.addAttribute("tags", tags);
		} else if (tagss==null) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("with author");
			String[] author1 = author.split(",");
			listPosts = postsServices.getAllPostsByAuthor(author1);
			model.addAttribute("listPosts", listPosts);
			model.addAttribute("authorsList", authorsList);
			model.addAttribute("tags", tags);
		} else {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("with author and tagss");
			String[] author1 = author.split(",");
			String[] tagss1 = tagss.split(",");

//			Page<Posts> page = postsServices.findPaginatedWithFilter(pageNo, pageSize,author1,tagss1);
//			listPosts = postsServices.getAllPosts(author1, tagss1,pageNo, pageSize);
			listPosts = postsServices.getAllPosts(author1,tagss1);
//			listPosts = page.getContent();
			model.addAttribute("listPosts", listPosts);
			model.addAttribute("authorsList", authorsList);
			model.addAttribute("tags", tags);

//			model.addAttribute("currentPage", pageNo);
//			model.addAttribute("totalPages",page.getTotalPages());
//			model.addAttribute("totalItems",page.getTotalElements());


		}

		return "index";
	}
	
}














