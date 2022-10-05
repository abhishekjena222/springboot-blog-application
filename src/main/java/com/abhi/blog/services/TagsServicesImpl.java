package com.abhi.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.blog.model.Tags;
import com.abhi.blog.repositories.TagsRepository;

@Service
public class TagsServicesImpl implements TagsServices{
	
	@Autowired
	private TagsRepository tagsRepositories;

	@Override
	public List<Tags> getAllTags() {
		return tagsRepositories.findAll();
	}

	@Override
	public void saveTags(Tags tags) {
		this.tagsRepositories.save(tags);		
	}

	@Override
	public void deleteTagById(long id) {
		this.tagsRepositories.deleteById(id);
		
	}

	@Override
	public Tags getTagsById(long id) {
		Optional<Tags> optional = tagsRepositories.findById(id);
		Tags tags = null;
		if(optional.isPresent()) {
			tags = optional.get();
		}
		else {
			throw new RuntimeException("Tag not found for id :: " + id);
		}
		return tags;
	}

	@Override
	public Tags getTagsByName(String string) {

		return tagsRepositories.findByName(string);
		
	}

	

}
