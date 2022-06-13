package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import javax.swing.text.html.Option;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
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
        return postService.findAll(sort, size, (page - 1) * size);
    }

    @GetMapping("/post/{postId}")
    public Post findPost(@PathVariable("postId") Integer postId) throws PostNotFoundException {
        return postService.findPostById(postId);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

}
