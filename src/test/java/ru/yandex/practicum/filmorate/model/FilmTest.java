package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Создать фильм с пустым именем")
    void createFilmWithEmptyName() {
        Film film = Film.builder()
                .name(null)
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Создать фильм с длинным описанием")
    void createFilmWithTooLongDescription() {
        String description = "аааааа".repeat(200);
        Film film = Film.builder()
                .name("Фильм")
                .description(description)
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Создать фильм с отрицательной длительностью")
    void createFilmWithMinusDuration() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(-100)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить фильм с пустым названием")
    void updateFilmWithEmptyName() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        Film film2 = Film.builder()
                .name(null)
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить фильм с длинным описанием")
    void updateFilmWithTooLongDescription() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        restTemplate.postForLocation("/films", film);
        String description = "аааааа".repeat(200);
        Film film2 = Film.builder()
                .name("Фильм")
                .description(description)
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить фильм с отрицательной длительностью")
    void updateFilmWithMinusDuration() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(100)
                .build();
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);
        Film film2 = Film.builder()
                .name("Фильм")
                .description("Интересный")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(-100)
                .build();
        HttpEntity<Film> entity = new HttpEntity<>(film2);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }
}