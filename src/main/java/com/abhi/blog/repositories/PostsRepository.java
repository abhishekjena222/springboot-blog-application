package com.abhi.blog.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abhi.blog.model.Posts;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

//    @Query(value = "SELECT p FROM Posts p " +
//            "WHERE p.title LIKE %?1%"
//            + "OR p.author LIKE %?1%"
//            + "OR p.content LIKE %?1%")
//    public List<Posts> findAllByKey(String keyword);

    @Query(value = "SELECT p FROM Posts p " +
            "WHERE concat(p.title, p.author, p.content) LIKE %?1%")
    public List<Posts> findAllByKey(String keyword);

    List<Posts> findByAuthorAndTags_Name(String author, String name, Pageable pageable);

    @Query("select p from Posts p inner join p.tags tags where p.author = ?1 and tags.createdAt = ?2")
    List<Posts> findByAuthorAndTags_CreatedAt(String author, LocalDate createdAt);
}
