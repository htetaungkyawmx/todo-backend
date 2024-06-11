package org.example.todobackend.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.todobackend.dto.TodoDto;
import org.example.todobackend.entity.Todo;
import org.example.todobackend.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
        TodoDto saveTodo = todoService.create(todoDto);
        return new ResponseEntity<>(saveTodo, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todoDtoList = todoService.getAllTodos();
        return ResponseEntity.ok(todoDtoList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id,
                                              @RequestBody TodoDto todoDto) {
        TodoDto updateTodo = todoService.updateTodo(todoDto,id);
        return new ResponseEntity<>(updateTodo, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{id}/completed")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id) {
        TodoDto updatedTodo = todoService.completedTodo(id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
    @PatchMapping("/{id}/incompleted")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id) {
        TodoDto updatedTodo = todoService.inCompletedTodo(id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
}
