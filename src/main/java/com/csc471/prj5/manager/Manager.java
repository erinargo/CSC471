package com.csc471.prj5.manager;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "manager")
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter

public class Manager {
    @Id
    private int employee;
    private int department;
    private int OfficeNum;
    private Date StartDate;

    public Manager(int employee, int department, int OfficeNum, Date StartDate) {
        this.employee = employee;
        this.department = department;
        this.OfficeNum = OfficeNum;
        this.StartDate = StartDate;
    }
}
