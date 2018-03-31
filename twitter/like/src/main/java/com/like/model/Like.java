package com.like.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "like")
@JsonFormat
public class Like {
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "lk_usr_id")
    @NotNull
    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @Column(name = "lk_creation_date")
    @NotNull
    private Timestamp creationDate;

    @Column(name = "lk_pst_id")
    @Min(0)
    private Integer postId;

    @Column(name = "lk_cmt_id")
    @Min(0)
    private Integer commentId;

    public long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}