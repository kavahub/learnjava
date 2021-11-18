package io.github.kavahub.learnjava;

import lombok.Getter;

@Getter
public class Employee {
    private Integer id;
    private String name;
    private Integer departmentId;

    public Employee(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Employee(Integer id, String name, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
    }


}
