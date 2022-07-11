package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    @NotBlank
    private String id;

    @NotBlank
    private String nickname;

    @NotBlank
    private String userName;

    public User(String id, String nickname, String userName) {
        this.id = id;
        this.nickname = nickname;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
