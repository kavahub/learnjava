package io.github.kavahub.learnjava;

import lombok.Data;

@Data
public class Employee implements Comparable<Employee> {
    private Integer id;
    private String name;
    private int age;
    private double salary;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(String name, Integer age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != null && !id.equals(employee.id)) return false;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (id != null) {
            result = result + id.hashCode();
        }
        if (name != null) {
            result = result + name.hashCode();
        }
        return result;
    }

    @Override
    public int compareTo(Employee o) {
        Employee e = (Employee) o;
        return getName().compareTo(e.getName());
    }

}
