package com.cache.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@Table(name = "employees")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    String employeeId;
    String name;
    String email;
    Long dateOfBirth;
    double salary;
}


