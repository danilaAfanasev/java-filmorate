package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
public class User {

    private final Set<Integer> friends = new HashSet<>();

    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;

    private String name;

    @PastOrPresent
    @NotNull
    private LocalDate birthday;

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public void deleteFriend(Integer id) {
        friends.remove(id);
    }
}
