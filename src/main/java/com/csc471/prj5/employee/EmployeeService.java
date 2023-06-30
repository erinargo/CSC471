package com.csc471.prj5.employee;

import com.csc471.prj5.department.Department;
import com.csc471.prj5.department.DepartmentRepo;
import com.csc471.prj5.department.employees.DepartmentEmployee;
import com.csc471.prj5.department.employees.DepartmentEmployeesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo repo;
    @Autowired
    DepartmentEmployeesRepo departmentEmployeesRepo;

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee getEmployeeBySSN(int ssn) {
        return repo.getReferenceById(ssn);
    }

    public boolean employeeExists(int ssn) {
        return repo.existsById(ssn);
    }

    public List<Employee> getAllEmployeesByDepartment(int deptNum) {
        List<DepartmentEmployee> employees = departmentEmployeesRepo
                .findAll()
                .stream()
                .filter(el -> el.getDeptnum() == deptNum)
                .toList();
        return employees
                .stream()
                .map(el -> getEmployeeBySSN(el.getEmployee()))
                .toList();
    }

    public void save(Employee employee) {
        repo.save(employee);
    }

    public void delete(Employee employee) {
        repo.delete(employee);
    }
}
