package com.csc471.prj5.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    ManagerRepo repo;

    public List<Manager> getAllManagers() {
        return repo.findAll();
    }

    public Manager getManagerByDepartment(int deptNum) {
        List<Manager> managers = repo.findAll();
        return managers
                .stream()
                .filter(m -> m.getDepartment() == deptNum)
                .findFirst()
                .orElse(null);
    }

    public Manager getManager(int manager) {
        return repo.getReferenceById(manager);
    }

    public Boolean managerExistsByDepartment(int deptNum) {
        List<Manager> managers = repo.findAll();
        return managers
                .stream()
                .anyMatch(m -> m.getDepartment() == deptNum);
    }

    public void save(Manager manager) {
        repo.save(manager);
    }

    public void delete(Manager manager) {
        repo.delete(manager);
    }
}
