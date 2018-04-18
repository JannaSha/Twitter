package com.gateway.client;

import com.gateway.model.Comment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CommentClient {
    private String serviceUrl = "http://localhost:1111/comment";
    private RestTemplate restTemplate;

    public CommentClient() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity<Comment> getOneComment(long id) {
        return restTemplate.getForEntity(serviceUrl + String.format("/%d", id), Comment.class);
    }

    public Object getAllComments() {
        return restTemplate.getForEntity(serviceUrl + "/all", Comment.class);
    }

    public ResponseEntity<Comment[]> getAllCommentForPost(long id) {
        return restTemplate.getForEntity(
                serviceUrl + String.format("/post/%d/all", id), Comment[].class);
    }

    public Object createComment(Comment comment) {
        return restTemplate.postForEntity(serviceUrl + "/createpost", comment, Comment.class);
    }

    public Object modifyComment(long id, Comment post) {
        return restTemplate.exchange(serviceUrl + String.format("/modify/{%d}", id),
                HttpMethod.PUT, new HttpEntity<>(post, null), Comment.class);
    }

    public void deleteComment(long id) {
        restTemplate.delete(serviceUrl + String.format("/delete/%d", id));
    }
}

