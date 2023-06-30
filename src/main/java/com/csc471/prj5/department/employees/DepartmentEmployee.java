package com.csc471.prj5.department.employees;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department_employees")
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter

public class DepartmentEmployee {
    @Id
    int deptnum;
    int employee;

    public DepartmentEmployee(int deptNum, int employee) {
        this.deptnum = deptNum;
        this.employee = employee;
    }
}

