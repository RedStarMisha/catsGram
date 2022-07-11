package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@Service
public class UserService {

    private final UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
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

    public Optional<User> findUserById(String id) {
        return userDao.findUserById(id);
    }
}
