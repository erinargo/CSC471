package com.csc471.prj5.dependent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentRepo extends JpaRepository<Dependent, Integer> {

}
