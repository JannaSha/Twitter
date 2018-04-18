package com.gateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonFormat
public class PostExtended {
    @NotNull
    private Post post;
    private Comment[] comments;
    private Like[] likes;

    public PostExtended() {}

    public Post getPost() {
        return post;
    }

    public Comment[] getComments() {
        return comments;
    }

    public Like[] getLikes() {
        return likes;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public void setLikes(Like[] likes) {
        this.likes = likes;
    }
}
