package com.csc471.prj5.dependent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees/{employee}/dependents")
public class DependentController {
    @Autowired
    DependentService dependentService;

    @PostMapping("/create")
    public String createDependent(Dependent dependent) {
        dependentService.save(dependent);

        return "redirect:/employees";
    }

    @PostMapping("/delete")
    public String deleteDependent(int id) {
        Dependent dependent = dependentService.getDependentById(id);
        dependentService.delete(dependent);
        
        return "redirect:/employees";
    }

    @PostMapping("/edit")
    public String editDependent(Dependent dependent) {
        dependentService.save(dependent);

        return "redirect:/employees";
    }
}
