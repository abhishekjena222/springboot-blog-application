package com.abhi.blog.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	@Override
	public Page<Posts> findPaginatedWithFilter(int pageNo, int pageSize, String[] author, String[] tagss) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return postsRepositories.findByAuthorInAndTags_NameIn(author,tagss,pageable);
	}

	@Override
	public List<Posts> getAllPosts(String[] author1, String[] tagss1) {
		return postsRepositories.findByAuthorInAndTags_NameIn(author1,tagss1);
	}

	@Override
	public List<Posts> getAllFalsePosts(boolean b) {
		return postsRepositories.findByIsPublished(b);
	}

	@Override
	public Set<String> getAllAuthors() {
		System.out.println("------------------------------------------------------1--------------------------");
		System.out.println(postsRepositories.findAllAuthors());
		System.out.println("------------------------------------------------------2--------------------------");
		return postsRepositories.findAllAuthors();
	}


	@Override
	public List<Posts> getAllPostsByKey(String keyword) {

		return postsRepositories.findAllByKey(keyword);
	}



	@Override
	public List<Posts> getAllPostsByTags(String[] tagss1) {
		return postsRepositories.findByTags_NameIn(tagss1);
	}

	@Override
	public List<Posts> getAllPostsByAuthor(String[] author1) {
		return postsRepositories.findByAuthorIn(author1);
	}




}
