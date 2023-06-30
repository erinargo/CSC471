package com.csc471.prj5.department;

import com.csc471.prj5.department.employees.DepartmentEmployee;
import com.csc471.prj5.department.employees.DepartmentEmployeesRepo;
import com.csc471.prj5.department.locations.DepartmentLocation;
import com.csc471.prj5.department.locations.DepartmentLocationsRepo;
import com.csc471.prj5.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Abuse Streams. This is the most useful piece of advice I could ever give you.
@Service
public class DepartmentService {
    @Autowired
    DepartmentRepo repo;

    @Autowired
    DepartmentEmployeesRepo departmentEmployeeRepo;

    @Autowired
    DepartmentLocationsRepo departmentLocationsRepo;

    public List<Department> getAllDepartments() {
        return repo.findAll();
    }

    public List<Department> getDepartmentsByEmployee(Employee employee) {
        List<DepartmentEmployee> departmentEmployees = departmentEmployeeRepo
                .findAll()
                .stream()
                .filter(dep -> (dep.getEmployee() == employee.getSSN()))
                .toList();
        return departmentEmployees
                .stream()
                .map(d -> getDepartmentByDepNum(d.getDeptnum()))
                .toList();
    }

    public Department getDepartmentByDepNum(int depNum) {
        return repo.getReferenceById(depNum);
    }

    public DepartmentLocation getLocationByDepartment(int depNum) {
        return departmentLocationsRepo
                .findAll()
                .stream()
                .filter(loc -> loc.getDeptnum() == depNum).findFirst()
                .orElse(null);
    }

    public List<DepartmentLocation> getDepartmentLocations(Department department) {
        List<DepartmentLocation> departmentLocations = departmentLocationsRepo.findAll();
        return departmentLocations
                .stream()
                .filter(loc -> (loc.getDeptnum() == department.getDeptnum()))
                .toList();
    }

    public Boolean departmentExists(int deptnum) {
        return repo.existsById(deptnum);
    }

    public void save(Department department) {
        repo.save(department);
    }

    public void delete(Department department) {
        repo.delete(department);
    }
}
