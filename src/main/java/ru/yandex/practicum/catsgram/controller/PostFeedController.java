package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostFeedController {


    PostService service;

    @Autowired
    public PostFeedController(PostService service) {
        this.service = service;
    }

    @GetMapping("/feed/friends")
    public List<Post> getFriendsPosts(@RequestBody String posts) throws JsonProcessingException {
        FriendsPost friendsPost;
        try {
            String friends = new ObjectMapper().readValue(posts, String.class);
            friendsPost =
                    new ObjectMapper().readValue(friends, FriendsPost.class);
            new ObjectMapper().writeValueAsString(friendsPost);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не формат Json", e);
        }
        return friendsPost.getFriends().stream()
                .map((email) -> service.findUserPostsByEmail(email, friendsPost.sort, friendsPost.size))
                .flatMap(i -> i.stream())
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    private static class FriendsPost {
        @NotNull
        private String sort;
        @Positive
        private Integer size;
        @NotNull
        private List<String> friends;
    }
}
