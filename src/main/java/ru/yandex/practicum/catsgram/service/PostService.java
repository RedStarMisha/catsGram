package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final UserService userService;
    private final PostDao postDao;
    private final FollowDao followDao;


    private final List<Post> posts = new ArrayList<>();

    private int id = 1;
    public final static Comparator<Post> postComparator = Comparator.comparing(Post::getCreationDate);


    @Autowired
    public PostService(UserService userService, PostDao postDao, FollowDao followDao) {
        this.userService = userService;
        this.postDao = postDao;
        this.followDao = followDao;
    }

    public Collection<Post> findPostsByUser(String userId) {
        User user = userService.findUserById(userId)
                .orElseThrow(() ->new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."));

        return postDao.findPostsByUser(user);
    }

    public Collection<Post> findPostsByUser(String authorId, Integer size, String sort) {
        return findPostsByUser(authorId)
                .stream()
                .sorted((p0, p1) -> {
                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
                    if (sort.equals("desc")) {
                        comp = -1 * comp; //обратный порядок сортировки
                    }
                    return comp;
                })
                .limit(size)
                .collect(Collectors.toList());
    }
    public List<Post> findAll(String sort, int size, int from) {
        switch (sort) {
            case "asc":
                return posts.stream().sorted(postComparator).skip(from).limit(size).collect(Collectors.toList());
            case "desc":
                return posts.stream().sorted(postComparator.reversed()).skip(from).limit(size).collect(Collectors.toList());
            default:
                return posts;
        }
    }

    public Post create(Post post) {
//        if (userService.findUserById(post.getAuthor()) == null) {
//            throw new UserNotFoundException(post.getAuthor());
//        }
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

    public List<Post> findFollowerPosts(String userId, int size) {
        return followDao.getFollowFeed(userId, size);
    }
}
