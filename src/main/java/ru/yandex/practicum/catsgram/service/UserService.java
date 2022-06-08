package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    Map<String, User> userMap = new HashMap<>();

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    public User postUser(String userEmail, User user) throws UserAlreadyExistException {
        if (userMap.containsKey(userEmail)) {
            throw new UserAlreadyExistException();
        }
        userMap.put(userEmail, user);
        return user;
    }

    public User putUser(String userEmail, User user) {
        userMap.put(userEmail, user);
        return user;
    }

    public User findUserByEmail(String email) {
        if (!userMap.containsKey(email)) {
            return null;
        }
        return userMap.get(email);
    }
}
