package ru.yandex.practicum.catsgram.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String author) {
        super("Пользователь " + author + " не найден");
    }
}
