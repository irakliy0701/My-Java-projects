package ru.kurdashvili.springpostsapi.PostsApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kurdashvili.springpostsapi.PostsApi.models.Post;

@Component
public class PostDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;

    @Autowired
    public PostDAO(JdbcTemplate jdbcTemplate, RestTemplate restTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
    }

    public void fullDb(){
        for (int i = 1; i <= 100; i++){
            String url = "https://jsonplaceholder.typicode.com/posts/" + i;
            Post post = restTemplate.getForObject(url, Post.class);

            jdbcTemplate.update("INSERT INTO Posts(title, body) VALUES(?, ?)",
                    post.getTitle(), post.getBody());
        }
    }
}
