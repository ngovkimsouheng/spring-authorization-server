package kh.edu.istad.co.auth_server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home(@AuthenticationPrincipal UserDetails userDetails) {
        return Map.of(
            "status", "Authenticated",
            "username", userDetails != null ? userDetails.getUsername() : "anonymous"
        );
    }
}
