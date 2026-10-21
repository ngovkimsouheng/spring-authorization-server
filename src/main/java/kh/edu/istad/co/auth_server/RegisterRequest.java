package kh.edu.istad.co.auth_server;

public record RegisterRequest(
        String username,
        String password
) {}
