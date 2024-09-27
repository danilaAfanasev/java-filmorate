package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Model {
    protected int id;

    public Model(int id) {
        this.id = id;
    }
}
