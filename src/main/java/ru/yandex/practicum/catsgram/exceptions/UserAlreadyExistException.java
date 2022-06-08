package ru.yandex.practicum.catsgram.exceptions;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException() {
        super("Пользователь с такой почтой уже существует");
    }
}
