package com.example.EmployeeManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String contactNo, @RequestParam String password) {
        logger.info("📞 Attempting login with contactNo: " + contactNo);

        Optional<Employee> employeeOptional = employeeService.findByContactNo(contactNo);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            logger.info("🔍 Found employee: " + employee.getFirstName());
            logger.info("🔑 Stored Password Hash: " + employee.getPassword());
            logger.info("🔓 Entered Password: " + password);

            if (passwordEncoder.matches(password, employee.getPassword())) {
                logger.info("✅ Password Match!");
                return ResponseEntity.ok("✅ Login successful!");
            } else {
                logger.warn("❌ Invalid password!");
                return ResponseEntity.status(401).body("❌ Invalid credentials!");
            }
        } else {
            logger.warn("❌ Employee not found!");
            return ResponseEntity.status(404).body("❌ Employee not found!");
        }
    }
}
