package com.abhi.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abhi.blog.model.Posts;
import com.abhi.blog.repositories.PostsRepository;

@Service
public class PostsServicesImpl implements PostsServices{
	
	@Autowired
	private PostsRepository postsRepositories;
	
//	@Override
//	public List<Posts> getAllPosts(String keyword) {
//		if(keyword != null){
//			return postsRepositories.findAll(keyword);
//		}
//		return postsRepositories.findAll();
//	}

	@Override
	public List<Posts> getAllPosts() {
		return postsRepositories.findAll();
	}

	@Override
	public void savePosts(Posts posts) {
		postsRepositories.save(posts);
		
	}

	@Override
	public Posts getPostsById(long id) {
		Optional<Posts> optional = postsRepositories.findById(id);
		Posts posts = null;
		if(optional.isPresent()) {
			posts = optional.get();
		}
		else {
			throw new RuntimeException("Post not found for id :: " + id);
		}
		return posts;
	}

	@Override
	public void deletePostsById(long id) {
		this.postsRepositories.deleteById(id);
		
	}

	@Override
	public Page<Posts> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

		return this.postsRepositories.findAll(pageable);
	}

//	@Override
//	public Page<Posts> findPaginated(int pageNo, int pageSize, String sortField, String keyword) {
//		List<Posts> posts = postsRepositories.findAllByKey(keyword)
//		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, posts);
//		return this.postsRepositories.findAll(pageable);
//	}

	@Override
	public List<Posts> getAllPostsByKey(String keyword) {

		return postsRepositories.findAllByKey(keyword);
	}




//	@Override
//	public Page<Posts> findPaginated(int pageNo, int pageSize) {
//		 List<Posts> posts = postsRepositories.findAllByKey(keyword);
//		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
//		return this.postsRepositories.findAll(pageable);
//	}

//	@Override
//	public List<Posts> getAllWithSort(String field, String direction) {
//		return null;
//	}

//	@Override
//	public List<Posts> getAllWithSort(String field) {
//		return postsRepositories.findAll(Sort.by(field));
//	}


}
