package com.lehrerkalender.lehrerkalender.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    //UserDetails ist ein Container für die Kern-User-Information -> wird später durch Spring in Authentication-Objekte umgewandelt
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
