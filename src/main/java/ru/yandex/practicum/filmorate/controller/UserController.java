package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.User;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @Override
    public void setEmptyUserName(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
            entities.put(user.getId(), user);
        }
    }
}
