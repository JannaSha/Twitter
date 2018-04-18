package com.like.repo;

import com.like.model.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<Like, Long> {
    List<Like> findByUserId(long userId);
    List<Like> findAll(Pageable pageable);
    List<Like> findAll();
    List<Like> findAllByPostId(long id);
    List<Like> findAllByCommentId(long id);
    List<Like> findByPostIdAndUserId(long postId, long userId);
}
