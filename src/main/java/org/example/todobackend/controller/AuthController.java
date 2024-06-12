package org.example.todobackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.todobackend.dto.JwtResponse;
import org.example.todobackend.dto.LoginDto;
import org.example.todobackend.dto.RegisterDto;
import org.example.todobackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String>
    register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse>
            login(@RequestBody LoginDto loginDto){
        String response=authService.login(loginDto);
        System.out.println("Login: " + response);
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setAccessToken(response);
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }
}
