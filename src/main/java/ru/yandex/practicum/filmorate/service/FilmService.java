package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService extends AbstractService<Film> {
    private static final LocalDate DATE_LIMIT = LocalDate.from(LocalDateTime.of(1895, 12, 28, 0, 0));

    public InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        super(filmStorage);
        this.userStorage = userStorage;
    }

    @Override
    @Size(min = 1, max = 200)
    public boolean doValidate(Film film) {
        return !film.getReleaseDate().isBefore(DATE_LIMIT);
    }

    public void addLike(Integer filmId, Integer userId) {
        if (storage.getById(filmId) == null || userStorage.getById(userId) == null) {
            throw new NotFoundException("Объекта нет в списке");
        }
        getById(filmId).addLike(userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (storage.getById(filmId) == null || userStorage.getById(userId) == null) {
            throw new NotFoundException("Объекта нет в списке");
        }
        getById(filmId).deleteLike(userId);
    }

    public List<Film> getPopular(Integer count) {
        return storage.getAll()
                .stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
