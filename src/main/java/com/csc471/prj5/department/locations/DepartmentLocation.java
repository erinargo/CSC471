package com.csc471.prj5.department.locations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department_locations")
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter

public class DepartmentLocation {
    @Id
    private int deptnum;
    private String location;

    public DepartmentLocation(int deptNum, String location) {
        this.deptnum = deptNum;
        this.location = location;
    }
}
