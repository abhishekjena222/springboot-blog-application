package com.abhi.blog.services;

import java.util.List;

import com.abhi.blog.model.Comments;

public interface CommentsServices {
	List<Comments> getAllComments();
	void saveComments(Comments comments);
	void deleteCommentById(long id);
	Comments getCommentById(long id);

}
