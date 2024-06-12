package org.example.todobackend.security;

import lombok.RequiredArgsConstructor;
import org.example.todobackend.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        var user1 = User.withUsername("john")
//                .password("12345")
//                .roles("USER")
//                .build();
//        var user2 = User.withUsername("mary")
//                .password("12345")
//                .roles("ADMIN")
//                .build();
//        var uds = new InMemoryUserDetailsManager();
//        uds.createUser(user1);
//        uds.createUser(user2);
//        return uds;
//    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider =
//                new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration configuration)
    throws Exception {
        AuthenticationManager manager=configuration.getAuthenticationManager();

        return manager;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement( c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //http.authenticationProvider(authenticationProvider());
        http.authorizeHttpRequests( c -> c
//                .requestMatchers(HttpMethod.POST,"/api/**")
//                .hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET,"/api/**")
//                .hasAnyRole("ADMIN","USER")
//                .requestMatchers(HttpMethod.PATCH,"/api/**")
//                .hasAnyRole("ADMIN","USER")
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll() 
                .anyRequest().authenticated());
        http.csrf(c -> c.disable());
        http.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e ->
                e.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        return http.build();
    }
}
