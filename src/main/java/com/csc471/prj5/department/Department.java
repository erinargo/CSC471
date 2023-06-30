package com.csc471.prj5.department;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter

public class Department {
    @Id
    private int deptnum;
    private String deptname;
    private int numemp;

    public Department(int deptnum, String deptname) {
        this.deptnum = deptnum;
        this.deptname = deptname;
        this.numemp = 0;
    }
}
