package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Создать фильм с пустым названием")
    void createFilmWithEmptyName() {
        Film film = new Film(100, null, "Описание", LocalDate.now().minusYears(50), -100);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Создать фильм с длинным описанием")
    void createFilmWithTooLongDescription() {
        String description = "Описание".repeat(200);
        Film film = Film.builder().name("Фильм").description(description).
                releaseDate(LocalDate.now().minusYears(20)).duration(200).build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Создать фильм с отрицательной длительностью")
    void createFilmWithMinusDuration() {
        Film film = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(20), -100);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить фильм с пустым названием")
    void updateFilmWithEmptyName() {
        Film film = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(20), 100);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        Film film2 = new Film(1, null, "Описание", LocalDate.now().minusYears(20), 100);
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());

        ResponseEntity<Collection<Film>> response3 = restTemplate.exchange("/films", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
    }

    @Test
    @DisplayName("Обновить фильм с длинным описанием")
    void updateFilmWithTooLongDescription() {
        Film film = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(30), 100);
        restTemplate.postForLocation("/films", film);
        String description = "Описание".repeat(200);
        Film film2 = Film.builder().name("Фильм").description(description).
                releaseDate(LocalDate.now().minusYears(20)).duration(100).build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить фильм с отрицательной длительностью")
    void updateFilmWithMinusDuration() {
        Film film = new Film(100, "Фильм", "Описание", LocalDate.now().minusYears(20), 100);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        Film film2 = new Film(1, "Фильм", "Описание", LocalDate.now().minusYears(20), -100);
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }
}