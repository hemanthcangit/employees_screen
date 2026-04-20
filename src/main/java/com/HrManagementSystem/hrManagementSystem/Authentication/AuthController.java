package com.HrManagementSystem.hrManagementSystem.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    // Simple login (for now no DB validation)
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        // 👉 For now static validation (you can connect DB later)
        if ("admin".equals(username) && "admin123".equals(password)) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

}
