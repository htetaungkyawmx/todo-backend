package org.example.todobackend;

import lombok.RequiredArgsConstructor;
import org.example.todobackend.dao.RoleRepository;
import org.example.todobackend.dao.UserRepository;
import org.example.todobackend.entity.Role;
import org.example.todobackend.entity.Todo;
import org.example.todobackend.entity.User;
import org.example.todobackend.repository.TodoRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoBackendApplication {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Bean @Transactional @Profile("dev")
    public ApplicationRunner runner(){
        return args -> {
//            List.of(
//                    new Todo(null,"To write an essay","In English",false),
//                    new Todo(null,"To review a historical article","In Burmese",false),
//                    new Todo(null,"To solve the problem of Maths","Advanced Level",false)
//            ).forEach(todoRepository::save);
            User user1 =new User("John Doe","john","john@gmail.com",
                    passwordEncoder.encode("12345"));
            User user2 =new User("Mary","mary","mary@gmail.com",
                    passwordEncoder.encode("12345"));
            Role role1=new Role(null,"ROLE_ADMIN");
            Role role2=new Role(null,"ROLE_USER");

            user1.addRole(role1);
            user2.addRole(role2);
            roleRepository.save(role1);
            roleRepository.save(role2);
            userRepository.save(user1);
            userRepository.save(user2);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoBackendApplication.class, args);
    }

}
