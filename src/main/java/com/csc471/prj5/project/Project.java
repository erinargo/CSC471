package com.csc471.prj5.project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project")
@NoArgsConstructor
// @AllArgsConstructor
@Getter
@Setter

public class Project {
    @Id
    private int projnum;
    private String projname;
    private String projectdesc;

    public Project(String projname, int projnum, String projectdesc) {
        this.projname = projname;
        this.projnum = projnum;
        this.projectdesc = projectdesc;
    }
}
