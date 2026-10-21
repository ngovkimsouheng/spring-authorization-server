package kh.edu.istad.co.auth_server.controller;

import kh.edu.istad.co.auth_server.dto.RegisterRequest;
import kh.edu.istad.co.auth_server.entity.RoleEntity;
import kh.edu.istad.co.auth_server.entity.UserEntity;
import kh.edu.istad.co.auth_server.repository.RoleRepository;
import kh.edu.istad.co.auth_server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        RoleEntity defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        // DelegatingPasswordEncoder generates passwords prefixed with {bcrypt}...
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(defaultRole));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}