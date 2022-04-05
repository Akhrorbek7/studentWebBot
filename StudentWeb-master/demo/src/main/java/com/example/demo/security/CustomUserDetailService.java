package com.example.demo.security;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record CustomUserDetailService(
        UserRepository userRepository) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByPhoneAndDeletedAtNull(phone);
        if(optional.isEmpty()){
            throw new UserNotFoundException("Phone number not found");
        }
        return new CustomUserDetail(optional.get());
    }
}
