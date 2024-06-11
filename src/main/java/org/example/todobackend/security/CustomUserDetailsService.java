package org.example.todobackend.security;

import lombok.RequiredArgsConstructor;
import org.example.todobackend.dao.UserRepository;
import org.example.todobackend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        User user =userRepository.findByUsernameOrEmail(usernameOrEmail,
                usernameOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException(usernameOrEmail));
        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                grantedAuthorities
        );
    }
}
