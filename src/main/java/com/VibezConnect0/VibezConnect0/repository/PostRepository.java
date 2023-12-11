package com.VibezConnect0.VibezConnect0.repository;

import com.VibezConnect0.VibezConnect0.models.Post;
import com.VibezConnect0.VibezConnect0.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.author.username = :username")
    Optional<Post> findByAuthorUsername(@Param("username") String username);
}
