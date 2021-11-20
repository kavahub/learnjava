package io.github.kavahub.learnjava;

import lombok.Data;

@Data
public class Employee implements Comparable<Employee>{
    String name;
    int age;
    double salary;
    long mobile;
    
    
    public Employee(String name, int age, double salary, long mobile) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.mobile = mobile;
    }


    @Override
    public int compareTo(Employee argEmployee) {
        return name.compareTo(argEmployee.getName());
    }
}
