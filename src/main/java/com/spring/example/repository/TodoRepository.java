package com.spring.example.repository;

import com.spring.example.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Helper Interface to handle common interactions with our Data Store
 *
 * @Author Jack Phillips
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
