package com.abhi.blog.services;

import java.util.List;

import com.abhi.blog.model.Tags;

public interface TagsServices {
	List<Tags> getAllTags();
	void saveTags(Tags tags);
	void deleteTagById(long id);
	Tags getTagsById(long id);
	Tags getTagsByName(String string);

}
