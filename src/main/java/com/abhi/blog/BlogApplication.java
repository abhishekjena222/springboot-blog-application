package com.abhi.blog;

import java.util.List;

import com.abhi.blog.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abhi.blog.model.Tags;
import com.abhi.blog.repositories.TagsRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
		
		
	}

}
