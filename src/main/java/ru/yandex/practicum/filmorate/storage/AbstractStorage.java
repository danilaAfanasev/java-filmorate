package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public abstract class AbstractStorage<T extends Model> implements Storage<T> {
    protected final Map<Integer, T> entities = new HashMap<>();
    private int id = 1;

    @Override
    public Collection<T> getAll() {
        log.info("Список всех объектов: " + entities.size());
        return new ArrayList<>(entities.values());
    }

    @Override
    public T create(T obj) {
        obj.setId(id++);
        entities.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public T update(T obj) {
        if (!entities.containsKey(obj.getId())) {
            throw new NotFoundException("Объекта нет в списке");
        }
        entities.put(obj.getId(), obj);
        log.info("Объект успешно обновлен: " + obj);
        return obj;
    }

    @Override
    public T delete(Integer id) {
        T obj;
        if (entities.containsKey(id)) {
            obj = entities.get(id);
        } else {
            throw new NotFoundException("Объекта нет в списке");
        }
        return obj;
    }

    @Override
    public T getById(Integer id) {
        T obj;
        if (entities.containsKey(id)) {
            obj = entities.get(id);
        } else {
            throw new NotFoundException("Объекта нет в списке");
        }
        return obj;
    }
}