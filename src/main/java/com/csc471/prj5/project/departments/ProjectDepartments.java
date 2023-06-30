package com.csc471.prj5.project.departments;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_departments")
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter
public class ProjectDepartments {
    @Id
    private int projnum;
    private String projname;
    private int deptnum;

    public ProjectDepartments(String projName, int projNum, int deptNum) {
        this.projnum = projNum;
        this.projname = projName;
        this.deptnum = deptNum;
    }
}
