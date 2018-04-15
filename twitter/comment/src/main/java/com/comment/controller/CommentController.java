package com.comment.controller;

import com.comment.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

//    @PostMapping(path="/createcomment", produces="application/json")
//    public ResponseE{
//
//    }
}
