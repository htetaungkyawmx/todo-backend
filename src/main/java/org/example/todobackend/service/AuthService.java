package org.example.todobackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.todobackend.dao.RoleRepository;
import org.example.todobackend.dao.UserRepository;
import org.example.todobackend.dto.LoginDto;
import org.example.todobackend.dto.RegisterDto;
import org.example.todobackend.entity.User;
import org.example.todobackend.exception.TodoApiException;
import org.example.todobackend.provider.JwtTokenProvider;
import org.example.todobackend.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private  final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public String register(RegisterDto registerDto){
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,
                    "Email is already in use");
        }
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,
                    "Username is already in use");
        }
        registerDto.setPassword(passwordEncoder
                .encode(registerDto.getPassword()));
        User user= EntityUtils.toUser(registerDto);
        user.addRole(roleRepository.findByName("ROLE_USER")
                .orElseThrow(EntityNotFoundException::new));
        userRepository.save(user);
        return "success signup.";
    }

    public String login(LoginDto loginDto){
        Authentication authentication =authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return jwt;
    }

}
