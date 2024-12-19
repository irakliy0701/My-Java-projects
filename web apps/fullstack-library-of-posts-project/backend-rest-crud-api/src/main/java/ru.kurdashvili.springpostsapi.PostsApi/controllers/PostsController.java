package ru.kurdashvili.springpostsapi.PostsApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kurdashvili.springpostsapi.PostsApi.models.Post;
import ru.kurdashvili.springpostsapi.PostsApi.services.PostDAO;
import ru.kurdashvili.springpostsapi.PostsApi.services.PostsService;
import ru.kurdashvili.springpostsapi.PostsApi.util.PostErrorResponse;
import ru.kurdashvili.springpostsapi.PostsApi.util.PostNotCreatedException;
import ru.kurdashvili.springpostsapi.PostsApi.util.PostNotFoundException;
import ru.kurdashvili.springpostsapi.PostsApi.util.PostNotUpdatedException;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:8080")
public class PostsController {

    private final PostsService postsService;
    private final PostDAO postDAO;

//    private final ModelMapper modelMapper;

    @Autowired
    public PostsController(PostsService postsService, PostDAO postDAO) { // ModelMapper modelMapper
        this.postsService = postsService;
//        this.modelMapper = modelMapper;
        this.postDAO = postDAO;
    }

    @PatchMapping("/fullDB")
    public ResponseEntity<HttpStatus> fullDbWithPosts(){
        postDAO.fullDb();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<Post> getAllPosts(){
        return postsService.findAll();
    }

    @GetMapping("/getLimitPosts")
    public List<Post> getAnotherLimitPosts(@RequestParam("limit") int limit,
                                        @RequestParam("page") int page){
        return postsService.findLimitPosts(limit, page);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable("id") int id){
        return postsService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<PostErrorResponse> handleException(PostNotFoundException e){ // в аргументах указываем класс искл,
        // которое будет ловаить этот метод.
        PostErrorResponse response = new PostErrorResponse(
                "Post with this id was not found",
                System.currentTimeMillis()
        );

        // В HTTP ответе будет тело (response - наш объект ошибки, который будет
        // преобразован в JSON с помощью Jackson) и статус в заголовке (404)
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        // NOT_FOUND - 404 cтатус
    }

    @PostMapping("/createPost")
    public ResponseEntity<HttpStatus> create(@RequestBody Post newpost){
        postsService.save(newpost);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<PostErrorResponse> handleException(PostNotCreatedException e){ // в аргументах указываем класс искл,
        // которое будет ловаить этот метод.
        PostErrorResponse response = new PostErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        // В HTTP ответе будет тело (response - наш объект ошибки, который будет
        // преобразован в JSON с помощью Jackson) и статус в заголовке (404)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        postsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/updatePost")
    public ResponseEntity<HttpStatus> update(@RequestBody Post postToUpdate){ // должен получать пост с полем id
//        System.out.println(postToUpdate);
        postsService.update(postToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<PostErrorResponse> handleException(PostNotUpdatedException e){ // в аргументах указываем класс искл,
        // которое будет ловаить этот метод.
        PostErrorResponse response = new PostErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        // В HTTP ответе будет тело (response - наш объект ошибки, который будет
        // преобразован в JSON с помощью Jackson) и статус в заголовке (404)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
