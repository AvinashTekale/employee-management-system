package com.example.EmployeeManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

   private JWTUtil jwt ;
   private AuthenticationManager authm;
    private EmployeeService employeeService;

   public AuthController (AuthenticationManager authm,JWTUtil jwt, EmployeeService es){

       this.jwt=jwt;
       this.authm=authm;
       this.employeeService=es;
   }

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);





    @PostMapping("/login")
    public String login(@RequestBody LoginModel req) {
        logger.info("Attempting login with contactNo: " + req.getContactNo());
        authm.authenticate(new UsernamePasswordAuthenticationToken(req.getContactNo(), req.getPassword()));
        Employee userDetails = employeeService.findByContactNo(req.getContactNo());
        return jwt.generateToken(userDetails.getContactNo());
//        Optional<Employee> employeeOptional = employeeService.findByContactNo(req.getContactNo());
//
//        if (employeeOptional.isPresent()) {
//            Employee employee = employeeOptional.get();
//            logger.info(" Found employee: " + employee.getFirstName());
//            logger.info(" Stored Password Hash: " + employee.getPassword());
//            logger.info("Entered Password: " + req.getPassword());
//
//            if (passwordEncoder.matches(req.getPassword(), employee.getPassword())) {
//                logger.info("Password Match!");
//                return ResponseEntity.ok(" Login successful!");
//            } else {
//                logger.warn(" Invalid password!");
//                return ResponseEntity.status(401).body(" Invalid credentials!");
//            }
//        } else {
//            logger.warn(" Employee not found!");
//            return ResponseEntity.status(404).body(" Employee not found!");
//        }
  }
}
