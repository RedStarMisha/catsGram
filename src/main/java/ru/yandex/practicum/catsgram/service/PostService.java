package ru.yandex.practicum.catsgram.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    UserService userService;
    private int id = 1;
    private final Comparator<Post> comparator = Comparator.comparing(Post::getCreationDate);


    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }
    public List<Post> findAll(@Nullable String sort, int size, int from) {
        if (sort == null) {
            return posts;
        }
        if (size < 0 || from < 0) {
            throw new IllegalArgumentException();
        }
        switch (sort) {
            case "asc":
                return posts.stream().sorted(comparator).skip(from).limit(size).collect(Collectors.toList());
            case "desc":
                return posts.stream().sorted(comparator.reversed()).skip(from).limit(size).collect(Collectors.toList());
            default:
                return posts;
        }
    }

    public Post create(Post post) {
        if (userService.findUserByEmail(post.getAuthor()) == null) {
            throw new UserNotFoundException(post.getAuthor());
        }
        post.setId(id++);
        posts.add(post);
        return post;
    }

    public Post findPostById(Integer postId) throws PostNotFoundException {
        return posts.stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }

    public List<Post> findUserPostsByEmail(String email, String sort, int size) {
        switch (sort) {
            case "asc":
                return posts.stream().filter(post -> post.getAuthor().equals(email))
                        .sorted(comparator).limit(size).collect(Collectors.toList());
            case "desc":
                return posts.stream().filter(post -> post.getAuthor().equals(email))
                        .sorted(comparator.reversed()).limit(size).collect(Collectors.toList());
            default:
                return posts;
        }
    }
}
