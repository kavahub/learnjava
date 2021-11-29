package io.github.kavahub.learnjava;

import lombok.Data;

/**
 * 
 * （辅助类）
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Data
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
