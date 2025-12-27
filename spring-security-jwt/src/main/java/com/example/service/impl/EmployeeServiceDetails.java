package com.example.service.impl;

import com.example.entity.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceDetails implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceDetails(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(username);
        return employeeOptional
                .map(EmployeeDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"+username));
    }
}
