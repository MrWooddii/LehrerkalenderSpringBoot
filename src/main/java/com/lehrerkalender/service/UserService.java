package com.lehrerkalender.service;

import com.lehrerkalender.dao.UserRepository;
import com.lehrerkalender.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        user.setPassword(encryptPassword(user.getPassword()));
        user.setActive(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);

    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean mailIsUnique(String email) {
        Optional<String> unique = userRepository.findByEmail(email);
        return unique.isEmpty();
    }

    public boolean UsernameIsUnique(String username) {
        Optional<User> unique = userRepository.findByUsername(username);
        return unique.isEmpty();
    }

}
