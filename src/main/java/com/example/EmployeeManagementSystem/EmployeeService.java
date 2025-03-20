package com.example.EmployeeManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

//    public Employee saveEmployee(Employee employee) {
//        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
//            logger.info(" Raw Password: " + employee.getPassword());
//            String hashedPassword = passwordEncoder.encode(employee.getPassword());
//            employee.setPassword(hashedPassword);
//            logger.info("Hashed Password: " + hashedPassword);
//        }
//        return employeeRepository.save(employee);
//    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee findByContactNo(String contactNo) {
        return employeeRepository.findByContactNo(contactNo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByContactNo(username);


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getContactNo())
                .password(user.getPassword())
                .build();
    }
}
