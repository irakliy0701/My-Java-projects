package ru.kurdashvili.springpostsapi.PostsApi.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name="userId")
//    private int userId;

    @Column(name = "title")
//    @NotEmpty(message = "title should not be empty")
//    @Size(min = 2, max = 30, message = "title should be between 2 and 30 chars")
    private String title;

    @Column(name = "body")
//    @NotEmpty(message = "body should not be empty")
//    @Size(min = 2, max = 30, message = "body should be between 2 and 30 chars")
    private String body;

    // Констурктор по умолчанию нужен для Spring
    public Post(){}

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                //", userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
