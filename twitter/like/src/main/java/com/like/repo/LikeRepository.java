package com.like.repo;

import com.like.model.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<Like, Long> {
    public Like findById(long id);
    public List<Like> findByUserId(long userId);
    public List<Like> findAll(Pageable pageable);
}
