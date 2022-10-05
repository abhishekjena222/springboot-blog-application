package com.abhi.blog.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.abhi.blog.model.Posts;

public interface PostsServices {
	List<Posts> getAllPosts();
	void savePosts(Posts posts);
	Posts getPostsById(long id);
	void deletePostsById(long id);
	Page<Posts> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);



	List<Posts> getAllPostsByKey(String keyword);




//	Page<Posts> findPaginated(int pageNo, int pageSize);

//	List<Posts> getAllWithSort(String field, String direction);

//	List<Posts> getAllWithSort(String field);
}
