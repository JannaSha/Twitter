package com.gateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@JsonFormat
public class Post {
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Min(0)
    private long userId;

    @Length(max=3000)
    @NotNull
    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @NotNull
    private Timestamp creationDate;

    public Post() {}

    public Post(@Min(0) long id, @NotNull @Min(0) long userId,
                @Length(max = 3000) @NotNull String text,
                @NotNull Timestamp creationDate) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.creationDate = creationDate;
    }


    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
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

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
