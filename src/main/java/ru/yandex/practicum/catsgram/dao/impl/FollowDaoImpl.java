package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.model.Follow;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.PostService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FollowDaoImpl implements FollowDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final PostDao postDao;

    @Autowired
    public FollowDaoImpl(JdbcTemplate jdbcTemplate, PostDao postDao, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @Override
    public List<Post> getFollowFeed(String userId, int max) {
        String sql = "SELECT u.*\n" +
                "FROM cat_follow AS f\n" +
                "INNER JOIN cat_user AS u ON u.id=f.follower_id\n" +
                "WHERE user_id=?";
        List<Follow> followList = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Follow(userId, rs.getString("id")), userId);
        List<Post> followerPosts = followList.stream()
                .map(f->userDao.findUserById(f.getFollower()).get())
                .flatMap(user -> postDao.findPostsByUser(user).stream()).collect(Collectors.toList());
        if (followerPosts.isEmpty()) {
            throw new PostNotFoundException("Постов не найдено");
        }
        return followerPosts.stream()
                .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                .limit(max)
                .collect(Collectors.toList());
    }
}
