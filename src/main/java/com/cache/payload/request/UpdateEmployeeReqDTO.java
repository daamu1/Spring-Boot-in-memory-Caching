package com.cache.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEmployeeReqDTO {
    @NotBlank(message = "Employee ID is required")
    String employeeId;

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    LocalDate dob;

    @Positive(message = "Salary must be a positive number")
    double salary;
}
