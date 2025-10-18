package com.login_auth_api.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody; // Garante o import correto
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login_auth_api.domain.User;
import com.login_auth_api.dto.LoginRequestDTO;
import com.login_auth_api.dto.RegisterRequestDTO;
import com.login_auth_api.dto.ResponseDTO;
import com.login_auth_api.infra.security.TokenService;
import com.login_auth_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
                if (passwordEncoder.matches(body.password(), user.getPassword())) {
                    String token = this.tokenService.generateToken(user);
                    return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
                } 
                return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional< User> user
         = this.userRepository.findByEmail(body.email());
        
        if(user.isEmpty()) {
            User newuser = new User();
            newuser.setPassword(passwordEncoder.encode(body.password()));
            newuser.setEmail(body.email());
            newuser.setName(body.name());
           
            String token = this.tokenService.generateToken(newuser);
            return ResponseEntity.ok(new ResponseDTO(newuser.getName(), token));
                       
        } else {
            return ResponseEntity.badRequest().build();
        }

    }


}
