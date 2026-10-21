package kh.edu.istad.co.auth_server.controller;

import kh.edu.istad.co.auth_server.dto.RegisterRequest;
import kh.edu.istad.co.auth_server.entity.RoleEntity;
import kh.edu.istad.co.auth_server.entity.UserEntity;
import kh.edu.istad.co.auth_server.repository.RoleRepository;
import kh.edu.istad.co.auth_server.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
        }

        RoleEntity defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new RoleEntity("ROLE_USER")));

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
