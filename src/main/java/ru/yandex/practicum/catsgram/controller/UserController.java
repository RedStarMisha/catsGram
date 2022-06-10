package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping
    public User postNewUser(@RequestBody User user) throws UserAlreadyExistException {
        String userEmail = user.getEmail();
        return userService.postUser(userEmail, user);
    }

    @PutMapping
    public User putNewUser(@RequestBody User user) {
        String userEmail = user.getEmail();
        return userService.putUser(userEmail, user);
    }



}
