package com.gateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@JsonFormat
public class Like {
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long userId;

    @JsonFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @NotNull
    private Timestamp creationDate;

    @Min(0)
    private long postId;

    @Min(0)
    private long commentId;

    public Like(@NotNull long userId, @NotNull Timestamp creationDate,
                @Min(0) long postId) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.postId = postId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public long getPostId() {
        return postId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
}