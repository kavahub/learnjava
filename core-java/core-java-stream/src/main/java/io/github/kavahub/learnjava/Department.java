package io.github.kavahub.learnjava;

import lombok.Data;

@Data
public class Department {
    private Integer id;
    private String department;

    Department(Integer id, String department) {
        super();
        this.id = id;
        this.department = department;
    }
}
