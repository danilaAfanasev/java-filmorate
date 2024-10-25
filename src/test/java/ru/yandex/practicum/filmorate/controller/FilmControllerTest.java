package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;
    UserController userController;

    @BeforeEach
    public void start() {
        InMemoryUserStorage userStorage = new InMemoryUserStorage();
        InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
        FilmService filmService = new FilmService(filmStorage,userStorage);
        UserService userService = new UserService(userStorage);
        filmController = new FilmController(filmService);
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("Создать несуществующий фильм")
    void createUnlimitedReleasedFilm() {
        Film film = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(200), 100);

        ValidationException e = assertThrows(ValidationException.class, () -> filmController.create(film));

        assertEquals("Некорректная запись объекта", e.getMessage());
    }

    @Test
    @DisplayName("Обновить несуществующий фильм")
    void updateUnlimitedReleasedFilm() {
        Film film = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(50), 100);
        filmController.create(film);
        Film filmUpdate = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(230), 200);

        ValidationException e = assertThrows(ValidationException.class, () -> filmController.update(filmUpdate));

        assertEquals("Некорректная запись обновленного объекта", e.getMessage());
    }

    @Test
    @DisplayName("Добавить лайк фильму")
    void addLikeToFilm() {
        Film film = new Film(1, "Фильм", "Описание", LocalDate.now().minusYears(50), 100);
        User user = new User(1, "danila@yandex.ru", "danila", "Danila", LocalDate.now().minusYears(50));

        filmController.create(film);
        userController.create(user);
        filmController.addLike(1,1);

        assertEquals(1, film.getLikes().size());
    }
}