package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * 
 * {@link Object} 类型比较
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompareObjectTest {
    private Employee[] employees;
    private Employee[] employeesArrayWithNulls;
    private Employee[] someMoreEmployees;

    @BeforeAll
    public void initData() {
        employees = new Employee[] { new Employee("John", 25, 3000, 9922001), new Employee("Ace", 22, 2000, 5924001), new Employee("Keith", 35, 4000, 3924401) };
        employeesArrayWithNulls = new Employee[] { new Employee("John", 25, 3000, 9922001), null, new Employee("Ace", 22, 2000, 5924001), null, new Employee("Keith", 35, 4000, 3924401) };
        someMoreEmployees = new Employee[] { new Employee("Jake", 25, 3000, 9922001), new Employee("John", 22, 2000, 5924001), new Employee("Ace", 22, 3000, 6423001), new Employee("Keith", 35, 4000, 3924401) };

    }

    @Test
    public void whenComparing_thenSortedByName() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Arrays.sort(employees, employeeNameComparator);
        assertEquals("Ace", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Keith", employees[2].getName());
    }

    @Test
    public void whenComparingWithComparator_thenSortedByNameDesc() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName, (s1, s2) -> {
            return s2.compareTo(s1);
        });
        Arrays.sort(employees, employeeNameComparator);
        assertEquals("Keith", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Ace", employees[2].getName());
    }

    @Test
    public void whenReversed_thenSortedByNameDesc() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparatorReversed = employeeNameComparator.reversed();
        Arrays.sort(employees, employeeNameComparatorReversed);

        assertEquals("Keith", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Ace", employees[2].getName());
    }

    @Test
    public void whenComparingInt_thenSortedByAge() {
        Comparator<Employee> employeeAgeComparator = Comparator.comparingInt(Employee::getAge);
        Arrays.sort(employees, employeeAgeComparator);
        
        assertEquals("Ace", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Keith", employees[2].getName());
    }

    @Test
    public void whenComparingLong_thenSortedByMobile() {
        Comparator<Employee> employeeMobileComparator = Comparator.comparingLong(Employee::getMobile);
        Arrays.sort(employees, employeeMobileComparator);

        assertEquals("Keith", employees[0].getName());
        assertEquals("Ace", employees[1].getName());
        assertEquals("John", employees[2].getName());
    }

    @Test
    public void whenComparingDouble_thenSortedBySalary() {
        Comparator<Employee> employeeSalaryComparator = Comparator.comparingDouble(Employee::getSalary);
        Arrays.sort(employees, employeeSalaryComparator);

        assertEquals("Ace", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Keith", employees[2].getName());
    }

    @Test
    public void whenNaturalOrder_thenSortedByName() {
        // naturalOrder 按Employee类定义的比较函数
        Comparator<Employee> employeeNameComparator = Comparator.<Employee> naturalOrder();
        Arrays.sort(employees, employeeNameComparator);

        assertEquals("Ace", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Keith", employees[2].getName());
    }

    @Test
    public void whenReverseOrder_thenSortedByNameDesc() {
        Comparator<Employee> employeeNameComparator = Comparator.<Employee> reverseOrder();
        Arrays.sort(employees, employeeNameComparator);
        
        
        assertEquals("Keith", employees[0].getName());
        assertEquals("John", employees[1].getName());
        assertEquals("Ace", employees[2].getName());
    }

    @Test
    public void whenNullsFirst_thenSortedByNameWithNullsFirst() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparator_nullFirst = Comparator.nullsFirst(employeeNameComparator);
        Arrays.sort(employeesArrayWithNulls, employeeNameComparator_nullFirst);

        assertNull(employeesArrayWithNulls[0]);
        assertNull(employeesArrayWithNulls[1]);
        assertEquals("Ace", employeesArrayWithNulls[2].getName());
        assertEquals("John", employeesArrayWithNulls[3].getName());
        assertEquals("Keith", employeesArrayWithNulls[4].getName());
    }

    @Test
    public void whenNullsLast_thenSortedByNameWithNullsLast() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparator_nullLast = Comparator.nullsLast(employeeNameComparator);
        Arrays.sort(employeesArrayWithNulls, employeeNameComparator_nullLast);
        
        assertEquals("Ace", employeesArrayWithNulls[0].getName());
        assertEquals("John", employeesArrayWithNulls[1].getName());
        assertEquals("Keith", employeesArrayWithNulls[2].getName());
        assertNull(employeesArrayWithNulls[3]);
        assertNull(employeesArrayWithNulls[4]);
    }

    @Test
    public void whenThenComparing_thenSortedByAgeSalary() {
        Comparator<Employee> employee_Age_Name_Comparator = Comparator.comparing(Employee::getAge).thenComparing(Employee::getSalary);

        Arrays.sort(someMoreEmployees, employee_Age_Name_Comparator);

        assertEquals("John", someMoreEmployees[0].getName());
        assertEquals("Ace", someMoreEmployees[1].getName());
        assertEquals("Jake", someMoreEmployees[2].getName());
        assertEquals("Keith", someMoreEmployees[3].getName());
    }

    @Test
    public void whenThenComparing_thenSortedBySalaryAge() {
        Comparator<Employee> employee_Name_Age_Comparator = Comparator.comparing(Employee::getSalary).thenComparingInt(Employee::getAge);

        Arrays.sort(someMoreEmployees, employee_Name_Age_Comparator);

        assertEquals("John", someMoreEmployees[0].getName());
        assertEquals("Ace", someMoreEmployees[1].getName());
        assertEquals("Jake", someMoreEmployees[2].getName());
        assertEquals("Keith", someMoreEmployees[3].getName());
    }    
}
