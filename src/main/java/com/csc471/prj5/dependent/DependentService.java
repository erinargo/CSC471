package com.csc471.prj5.dependent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependentService {
    @Autowired
    DependentRepo repo;

    public List<Dependent> getAllDependents() {
        return repo.findAll();
    }

    public List<Dependent> getDependentsByEmployee(int ssn) {
        List<Dependent> dependents = getAllDependents();
        return dependents
                .stream()
                .filter(dependent -> dependent.getEmployee() == ssn)
                .toList();
    }

    public Dependent getDependentById(int dependent) {
        return repo.getReferenceById(dependent);
    }

    public void save(Dependent dependent) {
        repo.save(dependent);
    }

    public void delete(Dependent dependent) {
        repo.delete(dependent);
    }
}
