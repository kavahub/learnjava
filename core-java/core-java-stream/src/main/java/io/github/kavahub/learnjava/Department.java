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
public class Department {
    private Integer id;
    private String department;

    Department(Integer id, String department) {
        super();
        this.id = id;
        this.department = department;
    }
}
