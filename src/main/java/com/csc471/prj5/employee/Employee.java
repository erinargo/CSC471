package com.csc471.prj5.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter

public class Employee {
    @Id
    private int SSN;
    private String DOB;
    private String FName;
    private String LName;
    private String Minit;

    public Employee(int SSN, String DOB, String FName, String LName, String Minit) {
        this.SSN = SSN;
        this.DOB = DOB;
        this.FName = FName;
        this.LName = LName;
        this.Minit = Minit;
    }
}
