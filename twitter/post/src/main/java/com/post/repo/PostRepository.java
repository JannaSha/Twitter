package com.post.repo;

import com.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAll(Pageable pageable);
    List<Post> findAll();
    List<Post> findByUserId(long userId);
}
