package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    @Email
    private final String email;
    @NotBlank
    private final String nickname;
    @NotBlank
    @Past
    private final LocalDate birthdate;

//    public String getEmail() {
//        return email;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public LocalDate getBirthdate() {
//        return birthdate;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
