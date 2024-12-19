package ru.kurdashvili.springpostsapi.PostsApi.util;

public class PostNotUpdatedException extends RuntimeException{
    public PostNotUpdatedException(String msg){
        super(msg);
    }
}
