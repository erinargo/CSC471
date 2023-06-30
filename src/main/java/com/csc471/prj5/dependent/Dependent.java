package com.csc471.prj5.dependent;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dependent")
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter

public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int employee;
    private String Name;
    private String DOB;

    public Dependent(int employee, String Name, String DOB) {
        this.employee = employee;
        this.Name = Name;
        this.DOB = DOB;
    }
}
