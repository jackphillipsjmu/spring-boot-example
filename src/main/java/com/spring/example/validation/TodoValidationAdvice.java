package com.spring.example.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Controller advice which places any validation error messages into the body of a 400 response.
 *
 * @ControllerAdvice = Indicates the annotated class assists a "Controller"
 *
 * If you just have a Validator (ex. TodoValidator.java) and no @ControllerAdvice then the response
 * will have a structure similar to this:
 * <pre>
 *     {
 *     "timestamp" : ...,
 *     "status": 400,
 *     "error": "Bad Request",
 *     "org.springframework.web.bind.MethodArgumentNotValidException",
 *     "errors": ["codes":[...]]
 *     "message": "..."
 *     "path": "/"
 *     }
 * </pre>
 *
 * If you have a @ControllerAdvice then the errors will be returned in a 400 response body
 * simplifying the response to an array of String error messages, example:
 * [ "Error occurred processing request" ]
 *
 * @author Jack Phillips
 */
@ControllerAdvice
public class TodoValidationAdvice {

    // MessageSource = Strategy interface for resolving messages, with support for the parameterization
    // and internationalization of such messages.
    @Autowired
    private MessageSource messageSource;

    /**
     * Method captures MethodArgumentNotValidException errors from Controller and builds list of
     * String error messages
     *
     * @ExceptionHandler = Annotation for handling exceptions in specific handler classes and/or handler methods
     * @ResponseStatus =  Marks a method or exception class with the status code and reason that should be returned
     * @ResponseBody = Indicates a method return value should be bound to the web response body
     *
     * @param ex MethodArgumentNotValidException thrown by validator class
     * @return List of String error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> validationErrors(MethodArgumentNotValidException ex) {
        return getExceptionMessages(ex);
    }

    /**
     * Create String list of Errors pulled either from a list of FieldError objects
     * or list of ObjectError objects within the MethodArgumentNotValidException
     *
     * @param ex MethodArgumentNotValidException
     * @return List of String error messages
     */
    private List<String> getExceptionMessages(MethodArgumentNotValidException ex) {
        // Try to get errors from FieldErrors, otherwise use ObjectErrors
        if (!CollectionUtils.isEmpty(ex.getBindingResult().getFieldErrors()))
            return getFieldErrorStringList(ex.getBindingResult().getFieldErrors());
        else
            return getObjectErrorStringList(ex.getBindingResult().getAllErrors());
    }

    /**
     * Create String list of Errors pulled either from a list of FieldError objects
     *
     * @param fieldErrorList List of reasons for rejecting a specific field value
     * @return List of String error messages
     */
    private List<String> getFieldErrorStringList(List<FieldError> fieldErrorList) {
        List<String> messages = new ArrayList<String>();
        Locale currentLocale = LocaleContextHolder.getLocale();
        for (FieldError error : fieldErrorList) {

            String message = null;
            int index = 0;
            while (message == null && index < error.getCodes().length) {
                try {
                    message = messageSource.getMessage(error.getCodes()[index],
                            new Object[]{error.getField(), error.getRejectedValue()},
                            currentLocale);
                } catch (NoSuchMessageException messageEx) {
                // Ignore this exception and try the next message
                }
                index++;
            }
            if (message != null) {
                messages.add(message);
            }
        }
        return messages;
    }

    /**
     * Builds String List of error messages from a List of ObjectError objects
     * this method is here because sometimes the encapsulated FieldErrors in the exception
     * are not populated
     *
     * @param objErrorList List of ObjectError objects which are a global reason for rejecting an object
     * @return List of String error messages
     */
    private List<String> getObjectErrorStringList(List<ObjectError> objErrorList) {
        List<String> messages = new ArrayList<String>();
        for (ObjectError error : objErrorList) {
            String message;

            // Check if the default error code exists, if it does use it otherwise try to get the error code
            if (StringUtils.hasText(error.getDefaultMessage()))
                message = error.getDefaultMessage();
            else
                message = error.getCode();

            // Add error message to list if it exists
            if (StringUtils.hasText(message))
                messages.add(message);
        }
        return messages;
    }
}
