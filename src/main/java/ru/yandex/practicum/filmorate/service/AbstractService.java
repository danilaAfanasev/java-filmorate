package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@Slf4j
@Service
public abstract class AbstractService<T extends Model> {
    Storage<T> storage;

    protected AbstractService(Storage<T> storage) {
        this.storage = storage;
    }

    public Collection<T> getAll() {
        return storage.getAll();
    }

    public T create(T obj) {
        validate(obj, "Некорректная запись объекта");
        setEmptyUserName(obj);
        return storage.create(obj);
    }

    public T update(T obj) {
        validate(obj, "Некорректная запись обновленного объекта");
        setEmptyUserName(obj);
        return storage.update(obj);
    }

    public T delete(Integer id) {
        return storage.delete(id);
    }

    public T getById(Integer id) {
        return storage.getById(id);
    }

    protected void validate(T obj, String message) {
        if (!doValidate(obj)) {
            throw new ValidationException(message);
        }
    }

    protected boolean doValidate(T obj) {
        return true;
    }

    protected void setEmptyUserName(T obj) {
    }
}