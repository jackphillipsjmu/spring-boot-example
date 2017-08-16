package com.spring.example.controller;

import com.spring.example.service.TodoService;
import com.spring.example.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to handle requests to the Micro service
 *
 * TODO:
 * - Improve Swagger Documentation and implement static doc generation
 * - Add request validator so we ensure that data passed into our methods are valid
 * - Add API versioning
 *
 * @suthor Jack Phillips
 */
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> findAllTodos() {
        return todoService.findAll();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.saveTodo(todo);
    }

    @PutMapping
    public Todo updateTodo(@RequestBody Todo todo) {
        return todoService.updateTodo(todo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTodo(@PathVariable("id") Integer id) {
        todoService.deleteTodo(id);
    }

}
