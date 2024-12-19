package ru.kurdashvili.springpostsapi.PostsApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurdashvili.springpostsapi.PostsApi.models.Post;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {
}
