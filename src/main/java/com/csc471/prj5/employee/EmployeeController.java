package com.csc471.prj5.employee;

import com.csc471.prj5.department.DepartmentService;
import com.csc471.prj5.department.employees.DepartmentEmployeesRepo;
import com.csc471.prj5.dependent.Dependent;
import com.csc471.prj5.dependent.DependentRepo;
import com.csc471.prj5.dependent.DependentService;
import com.csc471.prj5.employee.pay.Pay;
import com.csc471.prj5.employee.pay.PayRepo;
import com.csc471.prj5.manager.Manager;
import com.csc471.prj5.manager.ManagerRepo;
import com.csc471.prj5.manager.ManagerService;
import com.csc471.prj5.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DependentService dependentService;
    @Autowired
    DependentRepo dependentRepo;
    @Autowired
    ManagerService managerService;

    @Autowired
    DepartmentEmployeesRepo departmentEmployeesRepo;

    @Autowired
    DepartmentService departmentService;
    @Autowired
    ProjectService projectService;

    @Autowired
    PayRepo payRepo;

    final double MINIMUM_WAGE = 7.25;

    @GetMapping("")
    public String employeeList(Model model) {
        model.addAttribute("employeeList",
                employeeService.getAllEmployees());
        return "employee-list";
    }

    @GetMapping("/{ssn}")
    public String employee(@PathVariable int ssn, Model model) {
        Employee employee = employeeService.getEmployeeBySSN(ssn);
        Pay pay = payRepo.getReferenceById(employee.getSSN());

        model.addAttribute("employee",
                employeeService.getEmployeeBySSN(ssn));
        model.addAttribute("department", departmentService.getDepartmentsByEmployee(employee));
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        model.addAttribute("projects", projectService);
        model.addAttribute("dependents", dependentService.getDependentsByEmployee(ssn));
        model.addAttribute("pay", pay);

        return "employee-edit";
    }

    @GetMapping("/add")
    public String addEmployee() {
        return "employee-add";
    }

    @PostMapping("/delete")
    public String deleteEmployee(int ssn) {
        Employee employee = employeeService.getEmployeeBySSN(ssn);

        employeeService.delete(employee);
        dependentRepo.deleteAll(dependentService.getDependentsByEmployee(employee.getSSN()));

        Manager manager = managerService.getManager(employee.getSSN());
        managerService.delete(manager);

        departmentEmployeesRepo.deleteAll(
                departmentEmployeesRepo
                .findAll()
                .stream()
                .filter(emp -> emp.getEmployee() == employee.getSSN())
                .toList()
        );

        return "redirect:/employees";
    }

    @PostMapping("/edit")
    public String editEmployee(Employee employee, int SalaryPay, double HourlyPay) {
        employeeService.save(employee);
        Pay pay = payRepo.getReferenceById(employee.getSSN());
        pay.setHourlypay(HourlyPay);
        pay.setSalarypay(SalaryPay);

        payRepo.save(pay);

        return "redirect:/employees/"+employee.getSSN();
    }

    @PostMapping("/create")
    public String createEmployee(Employee employee, double HourlyPay, int SalaryPay, RedirectAttributes re) {
        if(employee.getSSN() < 100000000) {
            re.addFlashAttribute("alert", "An Error Occurred: Social Security Number had less than nine digits");
            return "redirect:/employees/create";
        }

        if(employeeService.employeeExists(employee.getSSN())) {
            re.addFlashAttribute("alert", "An Error Occurred: An employee with that social security number already exists!");
            return "redirect:/employees/create";
        }

        employeeService.save(employee);

        if((SalaryPay > 0 && HourlyPay > 0) || (SalaryPay == 0 && HourlyPay < MINIMUM_WAGE)) {
            SalaryPay = 0;
            HourlyPay = MINIMUM_WAGE;
            re.addFlashAttribute("alert", "The employee created had conflicting wage inputs. To save " +
                    "you having to go through the process of creating the employee again, their info has been saved with the " +
                    "Federal Minimum wage of " + MINIMUM_WAGE + " if this is not what you wanted, please ensure to edit " +
                    employee.getFName() + " " + employee.getLName() + "'s file. SSN: " + employee.getSSN());
        }

        Pay pay = new Pay(SalaryPay, HourlyPay, employee.getSSN());

        payRepo.save(pay);

        return "redirect:/employees";
    }

    @GetMapping("/create")
    public String createView() {
        return "create-employee";
    }
}
