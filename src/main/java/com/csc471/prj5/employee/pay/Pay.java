package com.csc471.prj5.employee.pay;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pay")
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
public class Pay {
    @Id
    int employee;
    private int salarypay;
    private double hourlypay;

    public Pay(int salarypay, double hourlypay, int employee) {
        if(salarypay > 0 && hourlypay > 0) return;
        this.hourlypay = hourlypay;
        this.salarypay = salarypay;
        this.employee = employee;
    }
}
