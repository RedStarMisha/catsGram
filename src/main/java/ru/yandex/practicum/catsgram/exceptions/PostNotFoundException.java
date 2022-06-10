package ru.yandex.practicum.catsgram.exceptions;

public class PostNotFoundException extends Exception {

    public PostNotFoundException(String format) {
        super(format);
    }
}
