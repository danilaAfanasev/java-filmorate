package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.service.AbstractService;

import java.util.Collection;

@Slf4j
public abstract class Controller<T extends Model> {

    AbstractService service;

    @Autowired
    public Controller(AbstractService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<T> findAll() {
        log.info("Список всех объектов: " + service.getAll().size());
        return service.getAll();
    }

    @PostMapping
    public T create(@Valid @RequestBody T obj) {
        service.create(obj);
        log.info("Объект успешно добавлен: " + obj);
        return obj;
    }

    @PutMapping
    public T update(@Valid @RequestBody T obj) {
        service.update(obj);
        log.info("Объект успешно обновлен: " + obj);
        return obj;
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable Integer id) {
        log.debug("Удаленный объект с id: ", id);
        service.delete(id);
    }
}
