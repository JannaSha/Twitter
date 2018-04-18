package com.like.controller;


import com.like.model.Like;
import com.like.repo.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;


@RestController
@RequestMapping("like")
public class LikeController<T> {

    @Autowired
    private LikeRepository likeRepository;
    private static final Logger log = Logger.getLogger(LikeController.class);

    @PostMapping(path="/createlike", produces="application/json")
    public ResponseEntity<T> createNewLike(
            @Valid @RequestBody Like like,
            @RequestHeader HttpHeaders header) {
        if (likeRepository.findByPostIdAndUserId(
                like.getPostId(), like.getUserId()) == null) {
            Like savedLike = likeRepository.save(like);
            if (savedLike == null) {
                log.error("Error to create new like id = " + like.getId());
                throw new DataIntegrityViolationException("Error to create new like");
            }
            header.setLocation(ServletUriComponentsBuilder
                    .fromCurrentServletMapping().path("/like/{id}").build()
                    .expand(savedLike.getId()).toUri());
            log.info("Created new like id = " + like.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public Like getLikeById(@PathVariable long id) {
        Optional<Like> like = likeRepository.findById(id);
        if (!like.isPresent()) {
            log.error("Error to get like id = " + id);
            throw new EntityNotFoundException("No like by id!");
        }
        log.info("Got like id = " + id);
        return like.get();
    }

    @GetMapping(path="/all")
    public List<Like> getAllLikes() {
        log.info("Got all likes");
        return likeRepository.findAll();
    }

    @GetMapping(path="/post/{id}/all")
    public List<Like> getLikeByPostId(@PathVariable("id") long id) {
        log.info("Got all comments post id = " + id);
        return likeRepository.findAllByPostId(id);
    }

    @GetMapping(path="/comment/{id}/all")
    public List<Like> getLikeByCommentId(@PathVariable("id") long id) {
        log.info("Got all comments comment id = " + id);
        return likeRepository.findAllByCommentId(id);
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteLike(@PathVariable("id") long id) {
        if (likeRepository.existsById(id)) {
            log.info("Deleted like id = " + id);
            likeRepository.deleteById(id);
        }
    }

    @DeleteMapping(path="/delete/user/{userId}/post/{postId}")
    public void deleteLikeByPost(
            @PathVariable("postId") long postId,
            @PathVariable("userId") long userId) {
        List<Like> likes = likeRepository.findByPostIdAndUserId(postId, userId);
        if (likes != null && likes.size() >= 1) {
            log.info("Deleted like for post id = " + postId);
            likeRepository.deleteById(likes.get(0).getId());
        }
    }

}
