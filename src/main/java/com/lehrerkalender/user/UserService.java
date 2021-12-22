package com.lehrerkalender.user;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(encryptPassword(user.getPassword()));
        user.setActive(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean mailIsTaken(String email) {
        return userRepository.existsByEmailIsLike(email);
    }

    public boolean usernameIsTaken(String username) {
        return userRepository.existsByUsernameIsLike(username);
    }

}
