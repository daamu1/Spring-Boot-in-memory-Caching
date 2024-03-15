package com.cache.controller;

import com.cache.payload.request.NewEmployeeReqDTO;
import com.cache.payload.request.UpdateEmployeeReqDTO;
import com.cache.payload.response.EmployeeResDTO;
import com.cache.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewEmployee(@RequestBody @Valid NewEmployeeReqDTO newEmployeeReqDTO) {
        String response = employeeService.addNewEmployee(newEmployeeReqDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResDTO> getEmployee(@PathVariable String employeeId) {
        return ResponseEntity.of(Optional.ofNullable(employeeService.findEmployee(employeeId)));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
        String message = employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmployeeDetails(@RequestBody @Valid UpdateEmployeeReqDTO updateEmployeeReqDTO) {
        String message = employeeService.updateEmployeeDetails(updateEmployeeReqDTO);
        return ResponseEntity.ok(message);
    }

}
