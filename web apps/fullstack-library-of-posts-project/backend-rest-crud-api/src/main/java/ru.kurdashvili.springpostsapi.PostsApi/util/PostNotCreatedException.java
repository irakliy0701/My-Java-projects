package ru.kurdashvili.springpostsapi.PostsApi.util;

public class PostNotCreatedException extends RuntimeException{
    public PostNotCreatedException(String msg){
        super(msg);
    }
}
