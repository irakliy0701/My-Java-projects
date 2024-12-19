package ru.kurdashvili.spring_course.REST_App_with_DTO.util;

// Объекты этого класса будут отправляться при ошибке (когда будем делать
// get запрос на адрес с несущ человеком)
public class PersonErrorResponse {

    // Опишем те поля, который будут отправляться при ошибке:
    private String message;
    private long timestamp;

    public PersonErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

