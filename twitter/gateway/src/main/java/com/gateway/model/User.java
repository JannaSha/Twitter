package com.gateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
@JsonFormat
public class User {
    @Id
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "usr_first_name")
    @NotNull
    private String firstName;

    @Column(name = "usr_second_name")
    @NotNull
    private String secondName;

    @Column(name = "usr_country")
    @NotNull
    private String country;

    @Column(name = "usr_city")
    @NotNull
    private String city;

    @Column(name = "usr_sex")
    @NotNull
    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @Column(name = "usr_birth_date")
    private Timestamp birth_date;

    @Column(name = "usr_description")
    private String description;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getSex() {
        return sex;
    }

    public Timestamp getBirth_date() {
        return birth_date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirth_date(Timestamp birth_date) {
        this.birth_date = birth_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
