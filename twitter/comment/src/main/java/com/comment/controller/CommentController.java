package com.comment.controller;

import com.comment.model.Comment;
import com.comment.repo.CommentRepository;
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
@RequestMapping("comment")
public class CommentController<T> {

    @Autowired
    private CommentRepository commentRepository;
    private static final Logger log = Logger.getLogger(CommentController.class);

    @PostMapping(path="/createcomment", produces="application/json")
    public ResponseEntity<T> createNewComment(
            @Valid @RequestBody Comment comment,
            @RequestHeader HttpHeaders header) {
        Comment savedComment = commentRepository.save(comment);
        if (savedComment == null) {
            log.error("Error to create new comment id = " + comment.getId());
            throw new DataIntegrityViolationException("Error to create new comment");
        }
        header.setLocation(ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/comment/{id}").build()
                .expand(savedComment.getId()).toUri());
        log.info("Created new comment id = " + comment.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path="/modify/{id}", produces = "application/json")
    public ResponseEntity<T> modifyComment(
            @PathVariable("id") long id,
            @Valid @RequestBody Comment comment,
            @RequestHeader HttpHeaders header) {
        if (!commentRepository.existsById(comment.getId())) {
            log.error("Error to update comment id = " + comment.getId());
            throw new DataIntegrityViolationException("Error to update comment");
        }
        commentRepository.save(comment);
        header.setLocation(ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/comment/{id}").build()
                .expand(comment.getId()).toUri());
        log.info("Updated comment id = " + comment.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public Comment getCommentById(@PathVariable long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            log.error("Error to get comment id = " + id);
            throw new EntityNotFoundException("No comment by id!");
        }
        log.info("Got comment id = " + id);
        return comment.get();
    }

    @GetMapping(path="/all")
    public List<Comment> getAllComments() {
        log.info("Got all comments");
        return commentRepository.findAll();
    }

    @GetMapping(path="/post/{id}/all")
    public List<Comment> getCommentByPostId(@PathVariable("id") long id) {
        log.info("Got all comments post id = " + id);
        return commentRepository.findAllByPostId(id);
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<T> deleteComment(@PathVariable long id) {
        if (commentRepository.existsById(id)) {
            log.info("Deleted comment id = " + id);
            commentRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
