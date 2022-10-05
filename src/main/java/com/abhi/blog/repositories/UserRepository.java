package com.abhi.blog.repositories;

import com.abhi.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    @Query(value = "select u.email from User u where u.userName LIKE %?1%")
    Optional<User> findEmailByName(String name);
}
