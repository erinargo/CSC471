package com.csc471.prj5.project;

import com.csc471.prj5.department.DepartmentService;
import com.csc471.prj5.department.employees.DepartmentEmployeesRepo;
import com.csc471.prj5.dependent.DependentRepo;
import com.csc471.prj5.dependent.DependentService;
import com.csc471.prj5.manager.Manager;
import com.csc471.prj5.manager.ManagerService;
import com.csc471.prj5.project.departments.ProjectDepartments;
import com.csc471.prj5.project.departments.ProjectDepartmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectDepartmentsRepo projectDepartmentsRepo;
    @Autowired
    DepartmentService departmentService;

    @GetMapping("")
    public String projectList(Model model) {
        model.addAttribute("projectList",
                projectService.getAllProjects());
        return "projects";
    }

    @GetMapping("/add")
    public String projectCreate() {
        return "project-create";
    }

    @GetMapping("/{project}")
    public String project(@PathVariable String project, Model model) {
        model.addAttribute("project",
                projectService.getProjectByName(project));
        model.addAttribute("departmentList",
                departmentService.getAllDepartments());
        return "project-edit";
    }

    @PostMapping("/{proj}/delete")
    public String deleteProject(@PathVariable int proj) {
        Project project = projectService.getProjectByNum(proj);
        projectDepartmentsRepo.deleteAllById(Collections.singleton(project.getProjnum()));
        projectService.delete(project);

        return "redirect:/projects";
    }

    @PostMapping("/edit")
    public String editProject(Project project) {
        projectService.save(project);

        return "redirect:/projects/" + project.getProjnum();
    }

    @PostMapping("/create")
    public String createProject(Project project, RedirectAttributes re) {
        if(projectService.existsByName(project.getProjname()) || projectService.existsByNum(project.getProjnum())) {
            re.addFlashAttribute("alert", "A project with that name or number already exists!");
            return "redirect:/projects";
        }

        projectService.save(project);

        return "redirect:/projects";
    }

    @PostMapping("/assign-project")
    public String assignProject(String projName, int department, RedirectAttributes re) {
        Project project = projectService.getProjectByName(projName);

        if(departmentService.getDepartmentByDepNum(department) != null) {
            ProjectDepartments addDepartment = new ProjectDepartments(projName, project.getProjnum(), department);
            projectDepartmentsRepo.save(addDepartment);

            re.addFlashAttribute("alert", "Success!");
        } else {
            re.addFlashAttribute("alert", "Department not found");
        }

        return "redirect:/projects/"+project.getProjnum();
    }

    @PostMapping("/{department}/{projnum}/remove-project")
    public String removeProject(@PathVariable int projnum, @PathVariable int department, RedirectAttributes re) {
        Project project = projectService.getProjectByNum(projnum);

        if(project != null && departmentService.getDepartmentByDepNum(department) != null) {
            projectDepartmentsRepo.delete(
                    projectDepartmentsRepo
                            .findAll()
                            .stream()
                            .filter(proj -> (project.getProjname().equals(proj.getProjname())
                                    && proj.getDeptnum() == department))
                            .findAny()
                            .get()
            );

            re.addFlashAttribute("alert", "Success!");
        } else {
            re.addFlashAttribute("alert", "Department or project not found");
        }

        return "redirect:/projects";
    }
}
