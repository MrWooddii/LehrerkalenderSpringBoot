package com.lehrerkalender.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
