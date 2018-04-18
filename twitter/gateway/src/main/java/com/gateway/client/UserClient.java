package com.gateway.client;

import com.gateway.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserClient {
    private String serviceUrl = "http://localhost:55555/user";
    private RestTemplate restTemplate;

    public UserClient() {
        restTemplate = new RestTemplate();
    }

    public User getOneUser(long id) {
        return restTemplate.getForEntity(
                serviceUrl + String.format("/%d", id), User.class).getBody();
    }

    public Object getAllPosts() {
        return restTemplate.getForEntity(serviceUrl + "/all", User.class);
    }

    public ResponseEntity<User[]> getAllCommentForPost(long id) {
        return restTemplate.getForEntity(
                serviceUrl + String.format("/post/%d/all", id), User[].class);
    }

    public void modifyUser(User user) {
        restTemplate.put(serviceUrl + String.format("/%d/modify", user.getId()), user);
    }
}
