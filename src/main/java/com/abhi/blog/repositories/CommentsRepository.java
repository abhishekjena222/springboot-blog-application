package com.abhi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhi.blog.model.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>{

}
