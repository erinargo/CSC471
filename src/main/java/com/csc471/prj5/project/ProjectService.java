package com.csc471.prj5.project;

import com.csc471.prj5.project.departments.ProjectDepartments;
import com.csc471.prj5.project.departments.ProjectDepartmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepo repo;
    @Autowired
    ProjectDepartmentsRepo projectDepartmentsRepo;

    public List<Project> getAllProjects() {
        return repo.findAll();
    }

    public Project getProjectByName(String name) {
        return getAllProjects()
                .stream()
                .filter(proj -> proj.getProjname().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Project getProjectByNum(int num) {
        return getAllProjects()
                .stream()
                .filter(proj -> proj.getProjnum() == num)
                .findFirst()
                .orElse(null);
    }

    public Project getProjectByDepartment(int department) {
        ProjectDepartments dept = projectDepartmentsRepo
                .findAll()
                .stream()
                .filter(proj -> proj.getDeptnum() == department)
                .findFirst()
                .orElse(null);
        if(dept == null) return null;
        return getProjectByName(dept.getProjname());
    }

    public void save(Project project) {
        repo.save(project);
    }

    public void delete(Project project) {
        repo.delete(project);
    }

    public Boolean existsByName(String name) {
        return repo.findAll().stream().anyMatch(n -> n.getProjname().equals(name));
    }

    public Boolean existsByNum(int num) {
        return repo.existsById(num);
    }
}
