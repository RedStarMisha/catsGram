package ru.yandex.practicum.catsgram.model;

import lombok.Data;

@Data
public class Follow {
    private final String user;
    private final String follower;
}
