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
class UserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Создать пользователя с некорректной почтой")
    void createUserWithBadEmail() {
        User user = new User(100, "danila.ru", "danila", "Danila", LocalDate.now().minusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Создать пользователя с пустым логином")
    void createUserWithEmptyLogin() {
        User user = new User(100, "danila@yandex.ru", " ", "Danila", LocalDate.now().minusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Создать пользователя с пустым именем")
    void createUserWithEmptyName() {
        int id = 1;
        String login = "danila";
        User user = new User(id, "danila@yandex.ru", login, null, LocalDate.now().minusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals(login, response.getBody().getName());
    }

    @Test
    @DisplayName("Создать пользователя с будущей датой рождения")
    void createFutureBirthUser() {
        User user = new User(100, "danila@yandex.ru", "danila", "Danila", LocalDate.now().plusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить пустую почту пользователя")
    void updateUserToEmptyEmail() {
        User usr = new User(1, "danila@yandex.ru", "danila", "Danila", LocalDate.now().minusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", usr, User.class);

        User user2 = new User(1, null, "danila", "Danila", LocalDate.now().minusYears(20));
        HttpEntity<User> entity = new HttpEntity<>(user2);
        ResponseEntity<User> response2 = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить пустой логин пользователя")
    void updateUserToEmptyLogin() {
        User usr = new User(100, "danila@yandex.ru", "danila", "Danila", LocalDate.now().minusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", usr, User.class);
        User user = new User(100, "danila@yandex.ru", " ", "Danila", LocalDate.now().minusYears(20));
        HttpEntity<User> entity = new HttpEntity<>(user);
        ResponseEntity<User> response2 = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }

    @Test
    @DisplayName("Обновить будущую дату рождения пользователя")
    void updateFutureBirthUser() {
        User usr = new User(100, "danila@yandex.ru", "danila", "Danila", LocalDate.now().minusYears(20));
        ResponseEntity<User> response = restTemplate.postForEntity("/users", usr, User.class);
        User user = new User(100, "danila@yandex.ru", "danila", "Danila", LocalDate.now().plusYears(20));
        HttpEntity<User> entity = new HttpEntity<>(user);
        ResponseEntity<User> response2 = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }
}