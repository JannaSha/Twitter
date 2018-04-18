package com.comment.repo;

import com.comment.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByUserId(long userId);
    List<Comment> findAll(Pageable pageable);
    List<Comment> findAll();
    List<Comment> findAllByPostId(long id);

}
