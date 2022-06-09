package ru.yandex.practicum.catsgram.model;

import java.time.Instant;

public class Post {

    private final String author; // автор
    private final Instant creationDate = Instant.now(); // дата создания
    private final String description; // описание
    private final String photoUrl; // url-адрес фотографии

    public Post(String author, String description, String photoUrl) {
        this.author = author;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
