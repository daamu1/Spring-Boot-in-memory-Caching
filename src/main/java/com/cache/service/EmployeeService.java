package com.cache.service;

import com.cache.exception.GlobalException;
import com.cache.model.Employee;
import com.cache.payload.request.NewEmployeeReqDTO;
import com.cache.payload.request.UpdateEmployeeReqDTO;
import com.cache.payload.response.EmployeeResDTO;
import com.cache.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    @CacheEvict(cacheNames = {"employeeCache"}, key = "#employeeId")
    public String deleteEmployee(String employeeId) {
        employeeRepository.deleteByEmployeeId(employeeId);
        return "Employee deleted successfully";
    }
    @CachePut(cacheNames = {"employeeCache"}, key="#updateEmployeeReqDTO.employeeId")
    public String updateEmployeeDetails(UpdateEmployeeReqDTO updateEmployeeReqDTO) {
        Employee loggedEmployee = employeeRepository.findByEmployeeId(updateEmployeeReqDTO.getEmployeeId())
                .orElseThrow(() -> new GlobalException("Employee not found"));
        loggedEmployee.setEmail(updateEmployeeReqDTO.getEmail());
        loggedEmployee.setName(updateEmployeeReqDTO.getName());
        loggedEmployee.setSalary(updateEmployeeReqDTO.getSalary());
        loggedEmployee.setDateOfBirth(updateEmployeeReqDTO.getDob().atStartOfDay(ZoneOffset.UTC).toEpochSecond());
        employeeRepository.save(loggedEmployee);

        return "Employee details updated successfully!";
    }

    public String addNewEmployee(NewEmployeeReqDTO newEmployeeReqDTO) {
        // Check if an employee with the same email already exists
        employeeRepository.findByEmail(newEmployeeReqDTO.getEmail()).ifPresent(
                existingEmployee -> {
                    throw new GlobalException("Employee with the same email already exists");
                }
        );

        // Convert date of birth to epoch time (UTC)
        long epochTime = newEmployeeReqDTO.getDob().atStartOfDay(ZoneOffset.UTC).toEpochSecond();


        // Create and save the new employee
        Employee newEmployee = Employee
                .builder()
                .employeeId(UUID.randomUUID().toString())
                .dateOfBirth(epochTime)
                .email(newEmployeeReqDTO.getEmail())
                .name(newEmployeeReqDTO.getName())
                .salary(newEmployeeReqDTO.getSalary())
                .build();

        employeeRepository.save(newEmployee);

        return "Employee added successfully!";
    }

    @Cacheable(cacheNames = {"employeeCache"}, key = "#employeeId")
    public EmployeeResDTO findEmployee(String employeeId) {
        simulateBackendCall();
        // Simulate fetching an existing employee record from the database
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);
        return employeeOptional.map(this::mapToEmployeeResDTO).orElse(null);
    }

    private EmployeeResDTO mapToEmployeeResDTO(Employee employee) {
        Instant instant = Instant.ofEpochMilli(employee.getDateOfBirth());

        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(localZoneId);

        LocalDate dateOfBirth = zonedDateTime.toLocalDate();

        // Map Employee entity to EmployeeResDTO
        return EmployeeResDTO.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .dateOfBirth(dateOfBirth)
                .build();
    }

    public void simulateBackendCall() {
        try {
            System.out.println("------------- Going to sleep for 5 seconds to simulate Backend Delay -----------");
            Thread.sleep(50 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
