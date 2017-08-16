package com.spring.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * @Author Jack Phillips
 *
 * NOTES:
 *
 * @Entity = Specifies that the class is an entity
 *
 * @JsonIgnoreProperties = Will ignore all unknown properties when trying to
 * serialize/deserialize a POJO passed in from a web
 *
 * @Id = Identifies mapped column for the primary key of the entity
 *
 * @GeneratedValue = Provides for the specification of generation strategies for the values of primary keys
 *
 * GenerationType.Identity = Indicates that the persistence provider must assign
 * primary keys for the entity using a database identity column.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_complete")
    private Boolean isComplete;

    @Column
    private String message;

    public Todo() {
        /* Default no-arg constructor */
    }

    /** GETTERS AND SETTERS **/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
