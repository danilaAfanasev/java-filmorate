package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.RatingMpaDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class FilmorateApplicationTests {

	private final FilmDbStorage filmDbStorage;
	private final RatingMpaDbStorage ratingMpaDbStorage;
	private final UserDbStorage userDbStorage;

	@Test
	@DisplayName("Получить все фильмы")
	void getAllFilms() {
		Film film1 = Film.builder()
				.id(1)
				.name("Avatar")
				.description("blue peoples")
				.releaseDate(LocalDate.now().minusYears(20))
				.duration(180)
				.genres(new HashSet<>())
				.mpa(ratingMpaDbStorage.getRatingMpaById(1))
				.build();
		Film film2 = Film.builder()
				.id(1)
				.name("Avatar2")
				.description("blue peoples and green peoples")
				.releaseDate(LocalDate.now().minusYears(20))
				.duration(180)
				.genres(new HashSet<>())
				.mpa(ratingMpaDbStorage.getRatingMpaById(1))
				.build();
		filmDbStorage.create(film1);
		filmDbStorage.create(film2);
		Collection<Film> films = filmDbStorage.getAll();

		assertThat(films).hasSize(2);
	}

	@Test
	@DisplayName("Создать фильм")
	void createFilm() {
		Film film = Film.builder()
				.id(1)
				.name("Avatar")
				.description("blue peoples")
				.releaseDate(LocalDate.now().minusYears(20))
				.duration(180)
				.genres(new HashSet<>())
				.mpa(ratingMpaDbStorage.getRatingMpaById(1))
				.build();
		filmDbStorage.create(film);
		Film filmOptional = filmDbStorage.getById(1);

		assertEquals(filmOptional.getId(), 1);
	}

	@Test
	@DisplayName("Найти фильм по Id")
	void getFilmById() {
		Film film = Film.builder()
				.id(1)
				.name("Avatar")
				.description("blue peoples")
				.releaseDate(LocalDate.now().minusYears(20))
				.duration(180)
				.genres(new HashSet<>())
				.mpa(ratingMpaDbStorage.getRatingMpaById(1))
				.build();
		filmDbStorage.create(film);

		assertEquals(filmDbStorage.getById(1).getId(), film.getId());
	}

	@Test
	@DisplayName("Получить всех пользователей")
	public void getAllUsers() {
		User user1 = User.builder()
				.id(1)
				.name("Aleksandr")
				.login("sashajaaa")
				.email("sashajaaa@yandex.ru")
				.birthday(LocalDate.now().minusYears(20))
				.build();
		User user2 = User.builder()
				.id(2)
				.name("Aleksandr")
				.login("sashaja")
				.email("sashaja@inbox.ru")
				.birthday(LocalDate.now().minusYears(20))
				.build();
		userDbStorage.create(user1);
		userDbStorage.create(user2);
		Collection<User> users = userDbStorage.getAll();

		assertThat(users).contains(user1);
		assertThat(users).contains(user2);
	}

	@Test
	@DisplayName("Создать пользователя")
	public void createUser() {
		User user = User.builder()
				.name("Aleksandr")
				.login("sashajaaa")
				.email("sashajaaa@yandex.ru")
				.birthday(LocalDate.now().minusYears(20))
				.build();
		userDbStorage.create(user);
		User userOptional = userDbStorage.getById(1);

		assertEquals(userOptional.getId(), 1);
	}

	@Test
	@DisplayName("Найти пользователя по Id")
	public void getUserById() {
		User user = User.builder()
				.id(1)
				.name("Danila")
				.login("danila")
				.email("danila@yandex.ru")
				.birthday(LocalDate.now().minusYears(20))
				.build();
		userDbStorage.create(user);
		User userOptional = userDbStorage.getById(1);

		assertEquals(userOptional.getName(), "Danila");
	}
}
