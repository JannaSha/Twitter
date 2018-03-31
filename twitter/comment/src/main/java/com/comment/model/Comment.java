package com.comment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "comment")
@JsonFormat
public class Comment {
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cmt_usr_id")
    @NotNull
    @Min(0)
    private Integer userId;

    @Column(name = "cmt_pst_id")
    @NotNull
    @Min(0)
    private Integer postId;

    @Column(name = "cmt_text")
    @NotNull
    @Length(max=3000)
    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @Column(name = "cmt_creation_date")
    @NotNull
    private Timestamp creationDate;

    public long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
