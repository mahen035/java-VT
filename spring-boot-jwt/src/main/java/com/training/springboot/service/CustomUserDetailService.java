package com.training.springboot.service;
import com.training.springboot.model.User;
import com.training.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username);
        if (user.getUserName().equals(username)) {
           return org.springframework.security.core.userdetails.User
                   .builder()
                   .username(user.getUserName())
                   .password("{bcrypt}"+user.getPassword())
                   .roles(user.getRole())
                   .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
