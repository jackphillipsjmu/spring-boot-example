package com.spring.example.service;

import com.spring.example.model.Todo;
import com.spring.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle Todo related operations and logic
 *
 * @author Jack Phillips
 */
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo getTodo(Integer id) {
        return todoRepository.findOne(id);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.saveAndFlush(todo);
    }

    public Todo updateTodo(Todo todo) {
        return todoRepository.saveAndFlush(todo);
    }

    public void deleteTodo(Integer id) {
        todoRepository.delete(id);
    }
}
