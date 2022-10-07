package com.abhi.blog.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.abhi.blog.model.Posts;

public interface PostsServices {
	List<Posts> getAllPosts();
	void savePosts(Posts posts);
	Posts getPostsById(long id);
	void deletePostsById(long id);
	Page<Posts> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	Set<String> getAllAuthors();

	List<Posts> getAllPostsByKey(String keyword);

//	List<Posts> getAllPosts(String author, String tagss);

//	List<Posts> getAllPosts(String author, List<String> tagss);

//	List<Posts> getAllPosts(String[] author, String[] tagss,int pageNo, int pageSize);

	List<Posts> getAllPostsByTags(String[] tagss1);

	List<Posts> getAllPostsByAuthor(String[] author1);

	Page<Posts> findPaginatedWithFilter(int pageNo, int pageSize, String[] sortField, String[] sortDirection);


	List<Posts> getAllPosts(String[] author1, String[] tagss1);

	List<Posts> getAllFalsePosts(boolean b);
}
