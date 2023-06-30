package com.csc471.prj5.manager;

import com.csc471.prj5.department.DepartmentService;
import com.csc471.prj5.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/{employee}")
    public String managerView(@PathVariable int employee, Model model) {
        model.addAttribute("manager", managerService.getManager(employee));
        return "manager";
    }

    @PostMapping("/remove-manager")
    public String deleteManager(int dept) {
        Manager manager = managerService.getManagerByDepartment(dept);
        if(departmentService.getDepartmentByDepNum(manager.getDepartment()) == null) return "redirect:/";
        managerService.delete(manager);

        return "redirect:/departments/"+dept;
    }

    @PostMapping("/add-manager")
    public String addManager(int employee, int department, RedirectAttributes re) {
        if(!employeeService.employeeExists(employee)) {
            re.addFlashAttribute("alert", "That employee wasn't found. Please ensure that you have the correct SSN");
            return "redirect:/departments/"+department;
        }

        Manager manager = new Manager(employee, department, 0, new Date());
        managerService.save(manager);

        return "redirect:/departments/"+department;
    }
}
