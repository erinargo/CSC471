package com.csc471.prj5.department;

import com.csc471.prj5.department.employees.DepartmentEmployee;
import com.csc471.prj5.department.employees.DepartmentEmployeesRepo;
import com.csc471.prj5.department.locations.DepartmentLocation;
import com.csc471.prj5.department.locations.DepartmentLocationsRepo;
import com.csc471.prj5.employee.EmployeeService;
import com.csc471.prj5.manager.Manager;
import com.csc471.prj5.manager.ManagerService;
import com.csc471.prj5.project.Project;
import com.csc471.prj5.project.ProjectController;
import com.csc471.prj5.project.ProjectService;
import com.csc471.prj5.project.departments.ProjectDepartments;
import com.csc471.prj5.project.departments.ProjectDepartmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentEmployeesRepo departmentEmployeesRepo;

    @Autowired
    DepartmentLocationsRepo departmentLocationsRepo;

    @Autowired
    ManagerService managerService;

    @Autowired
    ProjectController projectController;
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectDepartmentsRepo prjRepo;

    @GetMapping("")
    public String departmentList(Model model) {
        model.addAttribute("departmentList",
                departmentService.getAllDepartments());
        return "departments";
    }

    @GetMapping("/add")
    public String departmentAddView() {
        return "department-add";
    }

    @GetMapping("/{department}")
    public String department(@PathVariable int department, Model model) {

        List<ProjectDepartments> projects = prjRepo.findAll()
                        .stream()
                                .filter(prj -> prj.getDeptnum() == department)
                                        .toList();

        model.addAttribute("department", departmentService.getDepartmentByDepNum(department));
        model.addAttribute("location", departmentService.getLocationByDepartment(department));
        model.addAttribute("manager", managerService.getManagerByDepartment(department));
        model.addAttribute("employees", employeeService.getAllEmployeesByDepartment(department));
        model.addAttribute("hasManager", managerService.managerExistsByDepartment(department));
        model.addAttribute("projects", projects);


        if(managerService.managerExistsByDepartment(department))
            model.addAttribute("managerUser", employeeService.getEmployeeBySSN(managerService.getManagerByDepartment(department).getEmployee()));

        return "department-edit";
    }

    @PostMapping("/create")
    public String createDepartment(Department department, RedirectAttributes re) {
        if(!departmentService.departmentExists(department.getDeptnum())) {
            departmentService.save(department);
        } else re.addFlashAttribute("alert", "A department with that name already exists!");

        return "redirect:/departments";
    }

    @PostMapping("/add-employee")
    public String addEmployee(int department, int employee, RedirectAttributes re) {

        if(employeeService.getEmployeeBySSN(employee) == null
                || departmentService.getDepartmentByDepNum(department) == null) {
            re.addFlashAttribute("alert",
                    "Something went wrong! Make sure you have the correct department and employee!");
            return "redirect:/departments";
        }

        DepartmentEmployee departmentEmployee =
                new DepartmentEmployee(department, employee);

        departmentEmployeesRepo.save(departmentEmployee);

        Department dept = departmentService.getDepartmentByDepNum(department);
        dept.setNumemp(dept.getNumemp() + 1);

        departmentService.save(dept);

        return "redirect:/departments";
    }

    @PostMapping("/add-location")
    public String addLocation(int department, String location, RedirectAttributes re) {

        if(departmentService.getDepartmentByDepNum(department) == null) {
            re.addFlashAttribute("alert",
                    "Something went wrong! Make sure you have the correct department!");
            return "redirect:/departments";
        }

        DepartmentLocation departmentLocation =
                new DepartmentLocation(department, location);

        departmentLocationsRepo.save(departmentLocation);
        return "redirect:/departments";
    }

    @PostMapping("/remove-location")
    public String removeLocation(int department, String location, RedirectAttributes re) {
        List<DepartmentLocation> departmentLocations = departmentLocationsRepo.findAll();
        DepartmentLocation deptLoc = departmentLocations
                .stream()
                .filter(loc -> (loc.getDeptnum() == department && loc.getLocation().equals(location)))
                .findFirst()
                .orElse(null);

        if(deptLoc != null) departmentLocationsRepo.delete(deptLoc);
        return "redirect:/departments";
    }

    @PostMapping("/remove-employee")
    public String removeEmployee(int employee, int department) {
        List<DepartmentEmployee> departmentEmployees = departmentEmployeesRepo.findAll();
        DepartmentEmployee deptEmp = departmentEmployees
                .stream()
                .filter(emp ->
                        (emp.getEmployee() == employee
                                && emp.getDeptnum() == department))
                .findFirst()
                .orElse(null);

        if(deptEmp != null) {
            departmentEmployeesRepo.delete(deptEmp);
            Department dept = departmentService
                    .getDepartmentByDepNum(department);

            dept.setNumemp(departmentEmployees.size() - 1);
            departmentService.save(dept);
        }
        return "redirect:/departments";
    }

    @PostMapping("/{deptart}/delete")
    public String deleteDepartment(@PathVariable int deptart) {
        Department department = departmentService.getDepartmentByDepNum(deptart);
        departmentService.delete(department);

        departmentEmployeesRepo.deleteAll(
                departmentEmployeesRepo
                .findAll()
                .stream()
                .filter(dept ->
                        (dept.getDeptnum() == department.getDeptnum()))
                .toList()
        );

        departmentLocationsRepo.deleteAll(
                departmentLocationsRepo
                        .findAll()
                        .stream()
                        .filter(dept ->
                                (dept.getDeptnum() == department.getDeptnum()))
                        .toList()
        );

        Manager manager = managerService.getManagerByDepartment(department.getDeptnum());

        managerService.delete(manager);

        Project project = projectService.getProjectByDepartment(department.getDeptnum());

        if(project != null) projectController.removeProject(project.getProjnum(), department.getDeptnum(), null);

        return "redirect:/departments";
    }

    @PostMapping("/edit")
    public String editDepartment(Department department) {
        departmentService.save(department);

        return "redirect:/departments/"+department.getDeptnum();
    }
}
