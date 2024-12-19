package ru.kurdashvili.springpostsapi.PostsApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.kurdashvili.springpostsapi.PostsApi.models.Post;
import ru.kurdashvili.springpostsapi.PostsApi.repositories.PostsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostsService {

    private final PostsRepository postsRepository;
//    private final RestTemplate restTemplate;
//    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostsService(PostsRepository postsRepository){ // RestTemplate restTemplate, JdbcTemplate jdbcTemplate) {
        this.postsRepository = postsRepository;
//        this.restTemplate = restTemplate;
//        this.jdbcTemplate = jdbcTemplate;
    }

//    public List<Post> findLimitPosts(int limit, int page) {
//        return postsRepository.findByUserId(page);
//    }

    public List<Post> findLimitPosts(int limit, int page){
//        List<Post> allposts = postsRepository.findAll();
//        List<ArrayList<Post>> index = new ArrayList<>();
//        ArrayList<Post> in = new ArrayList<>(); // limit
//        for (int i = 0; i < allposts.size(); i++){
//            if (i % limit == 0 && i > 0){
//                index.add(in);
//                in = new ArrayList<>(); // limit
//
//            }
//            in.add(allposts.get(i));
//            if (i == allposts.size() - 1){
//                index.add(in);
//                in = new ArrayList<>();
//            }
//        }
//        return index.get(num - 1);
        return postsRepository.findAll(PageRequest.of(page - 1, limit, Sort.by("id"))).getContent();
    }

    public Post findOne(int id) {
//        Optional<Post> foundPost = postsRepository.findById(id);
//        return foundPost.orElseThrow(PostNotFoundException::new);
        return postsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Post newpost) {
        //  если у последнего userId кол-во постов == 10, то
        //  нужно newpost.setUserId(lastUserId + 1);
        //  если же у последнего userId кол-во постов < 10, то
        //  нужно newpost.setUserId(lastUserId)
        postsRepository.save(newpost);
    }

    @Transactional
    public void delete(int id) {
        postsRepository.deleteById(id);
    }

    public List<Post> findAll() {
        return postsRepository.findAll();
    }

    @Transactional
    public void update(Post postToUpdate) {
        postsRepository.save(postToUpdate);
    }
}
