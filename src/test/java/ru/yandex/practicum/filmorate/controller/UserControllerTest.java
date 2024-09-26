package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    UserController userController;

    @BeforeEach
    public void start() {
        userController = new UserController();
    }

    @Test
    @DisplayName("Создать пустое имя")
    void createEmptyName() {
        int id = 1;
        String login = "danila";
        User user = new User(id, "danila@yandex.ru", login, null, LocalDate.now().minusYears(50));
        userController.create(user);

        assertEquals(login, userController.entities.get(id).getName());
    }

    @Test
    @DisplayName("Обновить на пустое имя")
    void updateUserNameToEmpty() {
        User user1 = new User(1, "danila@yandex.ru", "danila", "", LocalDate.now().minusYears(50));
        userController.create(user1);
        int id = 1;
        String login = "danila";
        User user = new User(id, "danila@yandex.ru", login, null, LocalDate.now().minusYears(50));
        userController.update(user);

        assertEquals(login, userController.entities.get(id).getName());
    }
}