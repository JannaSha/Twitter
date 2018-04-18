package com.gateway.client;

import com.gateway.model.Post;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class PostClient {
    private String serviceUrl = "http://localhost:4444/post";
    private RestTemplate restTemplate;

    public PostClient() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity<Post> getOnePost(long id) {
        return restTemplate.getForEntity(serviceUrl + String.format("/%d", id), Post.class);
    }

    public ResponseEntity<Post[]> getAllPosts() {
        return restTemplate.getForEntity(serviceUrl + "/all", Post[].class);
    }

    public ResponseEntity<Post[]> getAllPotsForUser(long id) {
        return restTemplate.getForEntity(
                serviceUrl + String.format("/user/%d/all", id), Post[].class);
    }

    public Object createPost(Post post) {
        return restTemplate.postForEntity(serviceUrl + "/createpost", post, Post.class);
    }

    public Object modifyPost(long id, Post post) {
        return restTemplate.exchange(serviceUrl + String.format("/modify/{%d}", id),
                HttpMethod.PUT, new HttpEntity<>(post, null), Post.class);
    }

    public void deletePost(long id) {
        restTemplate.delete(serviceUrl + String.format("/delete/%d", id));
    }
}