package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
public record AuthService(UserRepository userRepository,
                          JwtTokenUtil jwtTokenUtil) {
}
