package com.abhi.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.blog.model.Comments;
import com.abhi.blog.repositories.CommentsRepository;

@Service
public class CommentsServicesImpl implements CommentsServices{
	
	@Autowired
	private CommentsRepository commentsRepositories;

	@Override
	public List<Comments> getAllComments() {		
		return commentsRepositories.findAll();
	}

	@Override
	public void saveComments(Comments comments) {
		this.commentsRepositories.save(comments);
		
	}

	@Override
	public void deleteCommentById(long id) {
		this.commentsRepositories.deleteById(id);
		
	}

	@Override
	public Comments getCommentById(long id) {
		Optional<Comments> optional = commentsRepositories.findById(id);
		Comments comments = null;
		if(optional.isPresent()) {
			comments = optional.get();
		}
		else {
			throw new RuntimeException("Comment not find of ID:: " + id);
		}
		
		return comments;
	}

}
