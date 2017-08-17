package com.spring.example.controller;

import com.spring.example.repository.TodoRepository;
import com.spring.example.service.TodoService;
import com.spring.example.model.Todo;
import com.spring.example.validators.TodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller to handle requests to the Micro service
 *
 * TODO:
 * - Improve Swagger Documentation and implement static doc generation
 * - Add request validator so we ensure that data passed into our methods are valid
 *     - Complete, see class {@link TodoValidator} for example
 *
 * - Add API versioning
 *
 * @suthor Jack Phillips
 */
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepo;

    /**
     * Returns all of the Todo objects tored in the in memory database
     *
     * @GetMapping = Annotation for mapping HTTP {@code GET} requests onto specific handler
     * methods. This is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET)
     *
     * @return List of Todo objects
     */
    @GetMapping
    public List<Todo> findAllTodos() {
        return todoService.findAll();
    }

    /**
     * Returns a Todo object if it exists in our in memory database. This method displays an alternative to using
     * the @GetMapping annotation.
     *
     * @RequestMapping = Annotation for mapping web requests onto specific handler classes and/or handler methods.
     *
     * @param id the identifier of a Todo object
     * @return Todo
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Todo getTodo(@PathVariable("id") Integer id) {
        return todoRepo.findOne(id);
    }

    /**
     * Add Todo object to in memory database using POST request
     *
     * @Valid = Marks a property, method parameter or method return type for validation cascading.
     * this annotation binds to the WebDataBinder and in turn the TodoValidator. If invalid it will throw a
     * org.springframework.web.bind.MethodArgumentNotValidException which we can leave as is or capture using
     * @ControllerAdvice in a seperate class.
     *
     * @param todo Todo object to add to database
     * @return Todo the saved Todo object
     */
    @PostMapping
    public Todo addTodo(@RequestBody @Valid Todo todo) {
        return todoService.saveTodo(todo);
    }

    /**
     * Updates a Todo object using PUT request
     *
     * @param todo Todo object to update
     * @return Todo updated Todo object
     */
    @PutMapping
    public Todo updateTodo(@RequestBody Todo todo) {
        return todoService.updateTodo(todo);
    }

    /**
     * Deletes the specified Todo in our in memory database based upon the ID passed
     * into the {id} path variable
     *
     * @param id Integer ID of the Todo object
     */
    @DeleteMapping(value = "/{id}")
    public void deleteTodo(@PathVariable("id") Integer id) {
        todoService.deleteTodo(id);
    }

    /**
     * Verify Todo requests passed into the controller
     *
     * @InitBinder = Annotation that identifies methods which initialize the {@link WebDataBinder} which will be
     * used for populating command and form object arguments of annotated handler methods.
     *
     * @param webDataBinder = data binding from web request parameters
     */
    @InitBinder
    public void dataBinding(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new TodoValidator());
    }

}
