package com.lehrerkalender.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //UserDetailsService wird genutzt, um username, password und GrantedAuthorities für User zu erhalten
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/students/**").hasRole("USER")
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/motivation/**").hasRole("USER")
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/register").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .defaultSuccessUrl("/user/home")
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                .logout().permitAll()
                    .logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //muss definiert sein, damit kein Fehler beim kompilieren geworfen wird
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
