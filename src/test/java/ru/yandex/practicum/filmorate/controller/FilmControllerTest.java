package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    public void start() {
        filmController = new FilmController();
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
}