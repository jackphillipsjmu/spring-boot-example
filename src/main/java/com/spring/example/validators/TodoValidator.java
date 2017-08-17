package com.spring.example.validators;

import com.spring.example.model.Todo;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Class used to validate that a Todo object passed into a controller is in fact valid
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

    /**
     * Validates Todo Object target by checking to see if a message exists appending an error if not
     *
     * @param target Object to cast to Todo for validation
     * @param errors Stores and exposes information about data-binding and validation errors for a specific object.
     */
    @Override
    public void validate(Object target, Errors errors) {
        // Logic to determine if the Object target is valid
        Todo todo = (Todo) target;

        // Check to see the Todo object has a message
        Boolean valid = StringUtils.hasText(todo.getMessage());

        // If invalid then add the error to the Errors object
        // the corresponding exception is org.springframework.web.bind.MethodArgumentNotValidException
        if (!valid)
            errors.reject(DEFAULT_TODO_ERROR + " Please include a message in the body of the Todo request");
    }
}
