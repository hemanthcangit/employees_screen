package com.HrManagementSystem.hrManagementSystem.Authentication;

import com.HrManagementSystem.hrManagementSystem.DTO.LoginDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.companyRegistrationDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.employeeRequestDTO;
import com.HrManagementSystem.hrManagementSystem.Entity.Employee;
import com.HrManagementSystem.hrManagementSystem.Repository.employeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private employeeRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthService  authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody companyRegistrationDTO dto) {
        authService.registerCompany(dto);
        return ResponseEntity.ok("User created successfully");

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO req) {

        String token = authService.login(req);
        return ResponseEntity.ok(token);
    }
}
