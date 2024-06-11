package org.example.todobackend.util;

import org.example.todobackend.dto.RegisterDto;
import org.example.todobackend.dto.TodoDto;
import org.example.todobackend.entity.Todo;
import org.example.todobackend.entity.User;
import org.springframework.beans.BeanUtils;

public class EntityUtils {
    public static TodoDto todoDto(Todo todo) {
        TodoDto todoDto=new TodoDto();
        BeanUtils.copyProperties(todo,todoDto);
        return todoDto;
    }

    public static User toUser(RegisterDto registerDto){
        User user=new User();
        BeanUtils.copyProperties(registerDto,user);
        return user;
    }


    public static Todo toEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDto,todo);
        return todo;
    }
}
