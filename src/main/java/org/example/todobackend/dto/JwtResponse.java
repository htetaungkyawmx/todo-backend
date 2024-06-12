package org.example.todobackend.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
