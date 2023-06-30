package com.csc471.prj5.department.employees;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentEmployeesRepo extends JpaRepository<DepartmentEmployee, Integer> {
}
