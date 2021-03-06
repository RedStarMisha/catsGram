package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.IncorrectParameterException;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import javax.swing.text.html.Option;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    private final static Logger log = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/posts")
    public List<Post> findAll(@RequestParam(required = false) String sort,
                              @RequestParam(required = false, defaultValue = "0") int size,
                              @RequestParam(required = false, defaultValue = "0") int page) {
        //log.debug("Текущее количество постов: {}", posts.size());
        if (sort == null) {
            throw new IncorrectParameterException("sort");
        }
        if (size < 1) {
            throw new IncorrectParameterException("size");
        }
        if (page < 1) {
            throw new IncorrectParameterException("page");
        }
        return postService.findAll(sort, size, (page - 1) * size);
    }

    @GetMapping("/posts/follower/{userId}")
    public List<Post> getFollowerPosts (
            @PathVariable(name = "userId") String userId,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return postService.findFollowerPosts(userId, size);
    }

    @GetMapping("/post/{postId}")
    public Post findPost(@PathVariable("postId") Integer postId) throws PostNotFoundException {
        return postService.findPostById(postId);
    }

//    @GetMapping("/posts")
//    public Collection<Post> findAll(@RequestParam String userId) {
//        return postService.findPostsByUser(userId);
//    }

//    @PostMapping(value = "/post")
//    public Post create(@RequestBody Post post) {
//        return postService.create(post);
//    }

}
