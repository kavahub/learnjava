package io.github.kavahub.learnjava;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 雇员服务，添加，修改，查询雇员功能
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class EmployeeService {
    List<Employee> employeeList = EmployeeList.getInstance();

    public List<Employee> getAllEmployees() {       
        return employeeList;
    }

    public List<Employee> searchEmployeesByName(String name) {
        Comparator<Employee> groupByComparator = Comparator.comparing(Employee::getName)
                                                    .thenComparing(Employee::getLastName);
        List<Employee> result = employeeList
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(name) || e.getLastName().equalsIgnoreCase(name))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
        return result;
    }

    public Employee getEmployee(long id) throws Exception {
        Optional<Employee> match
                = employeeList.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The Employee id " + id + " not found");
        }
    }   

    public long addEmployee(Employee employee) {
        employeeList.add(employee);
        return employee.getId();
    }

    public boolean updateEmployee(Employee customer) {
        int matchIdx = 0;
        Optional<Employee> match = employeeList.stream()
                .filter(c -> c.getId() == customer.getId())
                .findFirst();
        if (match.isPresent()) {
            matchIdx = employeeList.indexOf(match.get());
            employeeList.set(matchIdx, customer);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEmployee(long id) {
        Predicate<Employee> employee = e -> e.getId() == id;
        if (employeeList.removeIf(employee)) {
            return true;
        } else {
            return false;
        }
    }
}
