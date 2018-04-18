package com.gateway.controller;


import com.gateway.client.CommentClient;
import com.gateway.client.LikeClient;
import com.gateway.client.PostClient;
import com.gateway.client.UserClient;
import com.gateway.model.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping(value = "/twitter" )
@EnableAutoConfiguration
@Validated
public class GatewayController<T> {

    private PostClient postClient = new PostClient();
    private CommentClient commentClient = new CommentClient();
    private LikeClient likeClient = new LikeClient();
    private UserClient userClient = new UserClient();

    // Получение всех постов с комментариями и лайками для определенного юзера
    @GetMapping(path="/user/{id}/posts", produces="application/json")
    public List<PostExtended> getAllPostByUserId(@PathVariable("id") long id) {
        Post[] posts = postClient.getAllPotsForUser(id).getBody();
        return  (posts != null) ? getExtendedPosts(posts) : null;
    }

    // Получение всех постов с комментариями и лайками
    @GetMapping(path="/posts", produces="application/json")
    public List<PostExtended> getAllPosts(@PathVariable("id") long id) {
        Post[] posts = postClient.getAllPosts().getBody();
        return  (posts != null) ? getExtendedPosts(posts) : null;
    }

    // Получние информации о пользователе
    @GetMapping(path="/user/{id}/info", produces="application/json")
    public User getUserInfo(@PathVariable("id") long id) {
        return userClient.getOneUser(id);
    }

    // Изменение информации о пользователе
    @PutMapping(path="/user/{id}/modify",
            consumes = "application/json", produces = "application/json")
    public void modifyUser(@PathVariable("id") long id,
                           @Valid @RequestBody User user) {
        userClient.modifyUser(user);
    }

    // Создание нового поста
    @PostMapping(path="/user/{id}/createpost",
            consumes = "application/json", produces = "application/json")
    public Object createPost(@PathVariable long userId,
                           @Valid @RequestBody Post post) {
        post.setUserId(userId);
        return postClient.createPost(post);
    }

    // Удаление поста
    @DeleteMapping(path="/user/{userId}/delete/post/{postId}")
    public void deletePost(@PathVariable("userId") long userId,
                           @PathVariable("postId") long postId) throws AccessDeniedException {
        ResponseEntity<Post> post = postClient.getOnePost(postId);
        if (post.getBody() != null && post.getBody().getUserId() != userId) {
            throw new AccessDeniedException("ERROR: can not delete this post" + postId);
        }
        postClient.deletePost(postId);
    }

    // Изменение поста
    @PutMapping(path="/user/{userId}/post/{postId}/modify",
            consumes = "application/json", produces = "application/json")
    public Object modifyPost(@PathVariable("userId") long userId,
                           @PathVariable("postId") long postId,
                           @Valid @RequestBody Post post) throws AccessDeniedException {
        ResponseEntity<Post> oldPost = postClient.getOnePost(postId);
        if (oldPost.getBody() != null && oldPost.getBody().getUserId() != userId) {
            throw new AccessDeniedException("ERROR: can not modify this post" + postId);
        }
        return postClient.modifyPost(postId, post);
    }

    // Создание комментария
    @PostMapping(path="/user/{id}/createcommet")
    public Object createComment(@PathVariable("id") long id,
                                @Valid @RequestBody Comment comment) {
        comment.setUserId(id);
        return commentClient.createComment(comment);
    }

    // Удаление комментария
    @DeleteMapping(path="/user/{userId}/delete/comment/{commentId}")
    public void deleteComment(@PathVariable("userId") long userId,
                           @PathVariable("commentId") long commentId) throws AccessDeniedException {
        ResponseEntity<Comment> comment = commentClient.getOneComment(commentId);
        if (comment.getBody() != null && comment.getBody().getUserId() != userId) {
            throw new AccessDeniedException("ERROR: can not delete this comment" + commentId);
        }
        commentClient.deleteComment(commentId);
    }

    // Изменение комментария
    @PutMapping(path="/user/{userId}/comment/{commentId}/modify",
            consumes = "application/json", produces = "application/json")
    public Object modifyComment(@PathVariable("userId") long userId,
                           @PathVariable("commentId") long commentId,
                           @Valid @RequestBody Comment comment) throws AccessDeniedException {
        ResponseEntity<Comment> oldComment = commentClient.getOneComment(commentId);
        if (oldComment.getBody() != null && oldComment.getBody().getUserId() != userId) {
            throw new AccessDeniedException("ERROR: can not modify this comment" + commentId);
        }
        return commentClient.modifyComment(commentId, comment);
    }

    // Создание лайка
    @PostMapping(path="/user/{userId}/createlike/post/{postId}")
    public Object createLike(@PathVariable("userId") long userId,
                             @PathVariable("postId") long postId) {
        Like like = new Like(userId, new Timestamp(System.currentTimeMillis()), postId);
        return likeClient.createLike(like);
    }

    // Удаление лайка
    @DeleteMapping(path="/user/{userId}/deletelike/post/{postId}")
    public void deleteLike(@PathVariable("userId") long userId,
                           @PathVariable("postId") long postId) {
       likeClient.deleteLikeByPostId(postId);
    }

    // Создание списка расширенных постов с комментариями и лайками для массива постов
    private List<PostExtended> getExtendedPosts(Post[] posts) {
        List postExtended = new LinkedList();
        for(Post post: posts) {
            PostExtended tempPost = new PostExtended();
            tempPost.setPost(post);
            tempPost.setComments(commentClient.getAllCommentForPost(post.getId()).getBody());
            tempPost.setLikes(likeClient.getAllLikeForPost(post.getId()).getBody());
            postExtended.add(tempPost);
        }
        return postExtended;
    }




}
