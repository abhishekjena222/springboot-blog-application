package com.abhi.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abhi.blog.model.Posts;

import java.util.List;
import java.util.Set;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

//    @Query(value = "SELECT p FROM Posts p " +
//            "WHERE p.title LIKE %?1%"
//            + "OR p.author LIKE %?1%"
//            + "OR p.content LIKE %?1%")
//    public List<Posts> findAllByKey(String keyword);

//    @Query(value = "SELECT p FROM Posts p  " +
//            "WHERE concat(p.title, p.author, p.content) LIKE %?1%")
//    public Page<Posts> findAllByKey(String keyword, Pageable pageable);

    @Query(value = "SELECT p FROM Posts p  " +
            "WHERE concat(p.title, p.author, p.content) LIKE %?1%")
    public List<Posts> findAllByKey(String keyword);

//    List<Posts> findByAuthorAndTags_Name(String author, String name, Pageable pageable);
//
//    @Query("select p from Posts p inner join p.tags tags where p.author = ?1 and tags.createdAt = ?2")
//    List<Posts> findByAuthorAndTags_CreatedAt(String author, List<String> createdAt);

    @Query("select distinct p.author from Posts p")
    public Set<String> findAllAuthors();


    @Query("select p from Posts p inner join p.tags tags where p.author in ?1 and tags.name in ?2")
    Page<Posts> findByAuthorInAndTags_NameIn(String[] authors, String[] names, Pageable pageable);

    @Query("select p from Posts p inner join p.tags tags where p.author in ?1 and tags.name in ?2")
    List<Posts> findByAuthorInAndTags_NameIn(String[] authors, String[] names);
    @Query("select p from Posts p inner join p.tags tags where p.author in ?1")
    List<Posts> findByAuthorIn(String[] authors);

    @Query("select p from Posts p inner join p.tags tags where tags.name in ?1")
    List<Posts> findByTags_NameIn(String[] names);

    @Query("select p from Posts p where p.isPublished = ?1")
    List<Posts> findByIsPublished(boolean isPublished);

    @Query("select p from Posts p where p.isPublished = true")
    Page<Posts> findAll(Pageable pageable);


//    @Query("select * from")
//    List<Posts> findAllByauthor(List<Posts> author);
}
