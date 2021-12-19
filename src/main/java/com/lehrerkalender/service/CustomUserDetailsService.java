package com.lehrerkalender.service;

import com.lehrerkalender.dao.UserRepository;
import com.lehrerkalender.entity.User;
import com.lehrerkalender.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// https://stackoverflow.com/questions/41770156/spring-add-custom-user-details-to-spring-security-user/41772510
// https://www.youtube.com/watch?v=TNt3GHuayXs

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //UserDetails ist ein Container für die Kern-User-Information -> wird später durch Spring in Authentication-Objekte umgewandelt
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            return new CustomUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
    }
}
