package com.gateway.client;

import com.gateway.model.Like;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LikeClient {
    private String serviceUrl = "http://localhost:3333/like";
    private RestTemplate restTemplate;

    public LikeClient() {
        restTemplate = new RestTemplate();
    }

    public Object getOnePost(long id) {
        return restTemplate.getForEntity(serviceUrl + String.format("/%d", id), Like.class);
    }

    public Object getAllPosts() {
        return restTemplate.getForEntity(serviceUrl + "/all", Like.class);
    }

    public ResponseEntity<Like[]> getAllLikeForPost(long id) {
        return restTemplate.getForEntity(
                serviceUrl + String.format("/post/%d/all", id), Like[].class);
    }

    public ResponseEntity<Like[]> getAllLikeForComment(long id) {
        return restTemplate.getForEntity(
                serviceUrl + String.format("/comment/%d/all", id), Like[].class);
    }
    public Object createLike(Like like) {
        return restTemplate.postForEntity(serviceUrl + "/createlike", like, Like.class);
    }

    public void deleteLikeByPostId(long postId) {
        restTemplate.delete(serviceUrl + String.format("/delete/post/%d", postId));
    }

}
