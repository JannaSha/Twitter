package com.post.controller;


import com.post.model.Post;
import com.post.repo.PostRepository;
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
@RequestMapping("post")
public class PostController<T> {

    @Autowired
    private PostRepository postRepository;
    private static final Logger log = Logger.getLogger(PostController.class);

    @PostMapping(path="/createpost", produces="application/json")
    public ResponseEntity<T> createNewPost(
            @Valid @RequestBody Post post,
            @RequestHeader HttpHeaders header) {
        Post savedPost = postRepository.save(post);
        if (savedPost == null) {
            log.error("Error to create new post id = " + post.getId());
            throw new DataIntegrityViolationException("Error to create new post");
        }
        header.setLocation(ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/post/{id}").build()
                .expand(savedPost.getId()).toUri());
        log.info("Created new post id = " + post.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path="/modify/{id}", produces = "application/json")
    public ResponseEntity<T> modifyPost(
            @PathVariable("id") long id,
            @Valid @RequestBody Post post,
            @RequestHeader HttpHeaders header) {
        Optional<Post> oldPost = postRepository.findById(post.getId());
        if (!oldPost.isPresent() || oldPost.get().getId() != id) {
            log.error("ERROR: Post do not exist id = " + post.getId());
            throw new DataIntegrityViolationException(
                    "ERROR: Post do not exist id = " + post.getId());
        }
        postRepository.save(new Post(post.getId(), oldPost.get().getUserId(),
                post.getText(), oldPost.get().getCreationDate()));
        header.setLocation(ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/post/{id}").build()
                .expand(post.getId()).toUri());
        log.info("Updated post id = " + post.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public Post getPostById(@PathVariable long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            log.error("Error to get post id = " + id);
            throw new EntityNotFoundException("No post by id!");
        }
        log.info("Got post id = " + id);
        return post.get();
    }

    @GetMapping(path="/all")
    public List<Post> getAllPosts() {
        log.info("Got all post");
        return postRepository.findAll();
    }

    @GetMapping(path="/user/{id}/all")
    public List<Post> getAllPotsForUser(@PathVariable("id") long id) {
        log.info("Got all post for user id = " + id);
        return postRepository.findByUserId(id);
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<T> deleteComment(@PathVariable("id") long id) {
        if (postRepository.existsById(id)) {
            log.info("Deleted post id = " + id);
            postRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
