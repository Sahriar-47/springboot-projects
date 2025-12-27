package com.example.controller;

import com.example.AuthRequest;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import com.example.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmployeeService employeeService;

    @Autowired
    public PublicController(AuthenticationManager authenticationManager, JwtService jwtService, EmployeeService employeeService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.employeeService = employeeService;
    }


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to Spring Security JWT";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("authentication failed");
        }
    }

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }
}
