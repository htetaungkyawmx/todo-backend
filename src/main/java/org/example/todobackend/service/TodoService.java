package org.example.todobackend.service;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.example.todobackend.dto.TodoDto;
import org.example.todobackend.entity.Todo;
import org.example.todobackend.exception.ResourceNotFoundException;
import org.example.todobackend.repository.TodoRepository;
import org.example.todobackend.util.EntityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TodoDto updateTodo(TodoDto todoDto, Long todoId) {
        if(todoRepository.existsById(todoId)) {
            todoDto.setId(todoId);
            return EntityUtils.todoDto(todoRepository
                    .save(EntityUtils.toEntity(todoDto)));
        }
        else throw new ResourceNotFoundException("Todo not found");
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public TodoDto completedTodo(Long todoId) {
        Todo todo = findTodoById(todoId);
        todo.setCompleted(true);
        return EntityUtils.todoDto(todoRepository.save(todo));
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TodoDto inCompletedTodo(Long todoId) {
        Todo todo = findTodoById(todoId);
        todo.setCompleted(false);
        return EntityUtils.todoDto(todoRepository.save(todo));
    }

    private Todo findTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTodo(Long id){
        if(todoRepository.existsById(id)){
            todoRepository.deleteById(id);
        }else
            throw new ResourceNotFoundException("Todo not found with id " + id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public TodoDto create(TodoDto todoDto) {
        return EntityUtils.todoDto(todoRepository.save(
                EntityUtils.toEntity(todoDto)
        ));
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TodoDto getTodo(Long id) {
        return todoRepository.findById(id)
                .map(EntityUtils::todoDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not found with id:"
                                + id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<TodoDto> getAllTodos(){
        return todoRepository.findAll()
                .stream()
                .map(EntityUtils::todoDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
