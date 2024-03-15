package com.cache.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResDTO {
    String employeeId;
    String name;
    String email;
    LocalDate dateOfBirth;
    double salary;
}
