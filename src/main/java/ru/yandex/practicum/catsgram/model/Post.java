package ru.yandex.practicum.catsgram.model;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class Post {

    private Integer id;
    private final User author; // автор
    private final LocalDate creationDate; // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии

    public Post(User author, String description, String photoUrl) {
        this.author = author;
        this.creationDate = LocalDate.now();
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public Post(Integer id, User author, String description, String photoUrl, LocalDate creationDate) {
        this.id = id;
        this.author = author;
        this.creationDate = creationDate;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public void setId(int id) {
        this.id = id;
    }



}
