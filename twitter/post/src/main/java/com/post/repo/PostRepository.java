package com.post.repo;

import com.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    public Post findById(long id);
    public List<Post> findByUserId(long userId);
    public List<Post> findAll(Pageable pageable);
}
