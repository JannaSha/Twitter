package com.user.repo;

import com.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findById(long id);
    public List<User> findByUserId(long userId);
    public List<User> findAll(Pageable pageable);
}
