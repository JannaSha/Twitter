package com.comment.repo;

import com.comment.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    public Comment findById(long id);
    public List<Comment> findByUserId(long userId);
    public List<Comment> findAll(Pageable pageable);
}
