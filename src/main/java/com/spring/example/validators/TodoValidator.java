package com.spring.example.validators;

import com.spring.example.model.Todo;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Class used to validate that a Todo object passed into a controller
 *
 * implements Validator = A validator for application-specific object
 *
 * @author Jack Phillips
 */
public class TodoValidator implements Validator {

    protected static final String DEFAULT_TODO_ERROR = "Cannot process Todo Request!";

    @Override
    public boolean supports(Class<?> clazz) {
        // Check to see if the validator supports the class
        return Todo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Logic to determine if the Object target is valid
        Todo todo = (Todo) target;

        // Check to see the Todo object has a message
        Boolean valid = StringUtils.hasText(todo.getMessage());

        // If invalid then add the error to the Errors object
        if (!valid)
            errors.reject("Invalid Todo request please include a message in the request", DEFAULT_TODO_ERROR);
    }
}
