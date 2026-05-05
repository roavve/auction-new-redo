package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new UsernameNotFoundException("Account is locked");
        }
        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new UsernameNotFoundException("Account is not active");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole() != null ? user.getRole() : "USER")
                .build();
    }

    @Transactional
    public boolean register(String firstName, String lastName, String email,
                            String password, String contactPhone) {
        if (userRepository.existsByEmail(email)) {
            return false;
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setStatus(1);
        user.setActive(true);
        user.setLocked(false);
        user.setContactPhone(contactPhone);
        user.setRegisterDate(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateLoginDate(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
            u.setLoginDate(LocalDateTime.now());
            userRepository.save(u);
        });
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}