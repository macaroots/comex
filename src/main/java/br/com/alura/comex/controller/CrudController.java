package br.com.alura.comex.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.comex.dto.Dto;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class CrudController<R extends CrudRepository> {

    public R repository;
    public CrudController(R repository) {
        this.repository = repository;
    }

    public abstract void doUpdate(Object entity, Dto form);

    public List<?> getDtos(Iterable<?> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::getDto).collect(Collectors.toList());
    }
    public Object getDto(Object entity) {
        return entity;
    }
    public Object getDetailedDto(Object entity) {
        return entity;
    }

    @GetMapping
    public Iterable<?> list() {
        Iterable<?> entities = repository.findAll();
        return getDtos(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            Object entity = repository.findById(id);
            entity.toString();
            return ResponseEntity.ok(getDetailedDto(entity));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> sendCreated(Object entity, String path, Object id, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path(path).buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(getDetailedDto(entity));
    }
    
    @Transactional
    public ResponseEntity<?> insert(Dto form, String path, UriComponentsBuilder uriBuilder) {
        Object entity = form.toEntity();
        repository.save(entity);
        try {
            return sendCreated(entity, path, form.getId(), uriBuilder);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Dto form) {
        try {
            Object entity = repository.findById(id);
            doUpdate(entity, form);
            return ResponseEntity.ok(getDetailedDto(entity));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
