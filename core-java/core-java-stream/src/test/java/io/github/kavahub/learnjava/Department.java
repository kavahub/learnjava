package io.github.kavahub.learnjava;

import lombok.Getter;

@Getter
public class Department {
    private Integer id;
    private String department;

    Department(Integer id, String department) {
        super();
        this.id = id;
        this.department = department;
    }
}
