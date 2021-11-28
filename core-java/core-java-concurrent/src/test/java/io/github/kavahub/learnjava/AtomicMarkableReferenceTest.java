package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.jupiter.api.Test;

import lombok.Data;

/**
 * {@link AtomicMarkableReference} 与 {@link AtomicStampedReference} 一样也可以解决 ABA的问题，两者唯一的区别是，
 * <code>AtomicStampedReference</code> 是通过 int 类型的版本号，而 AtomicMarkableReference 是通过 boolean
 * 型的标识来判断数据是否有更改过。
 * 
 * <p>
 * 既然有了 <code>AtomicStampedReference</code> 为啥还需要再提供 <code>AtomicMarkableReference</code>
 * 呢，在现实业务场景中，不关心引用变量被修改了几次， 只是单纯的关心是否更改过
 * 
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class AtomicMarkableReferenceTest {

    @Test
    void givenMarkValueAsTrue_whenUsingIsMarkedMethod_thenMarkValueShouldBeTrue() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenMarkValueAsFalse_whenUsingIsMarkedMethod_thenMarkValueShouldBeFalse() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, false);

        assertFalse(employeeNode.isMarked());
    }

    @Test
    void whenUsingGetReferenceMethod_thenCurrentReferenceShouldBeReturned() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        assertEquals(employee, employeeNode.getReference());
    }

    @Test
    void whenUsingGetMethod_thenCurrentReferenceAndMarkShouldBeReturned() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        boolean[] markHolder = new boolean[1];
        Employee currentEmployee = employeeNode.get(markHolder);

        assertEquals(employee, currentEmployee);
        assertTrue(markHolder[0]);
    }

    @Test
    void givenNewReferenceAndMark_whenUsingSetMethod_thenCurrentReferenceAndMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        Employee newEmployee = new Employee(124, "John");
        employeeNode.set(newEmployee, false);

        assertEquals(newEmployee, employeeNode.getReference());
        assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenTheSameObjectReference_whenUsingAttemptMarkMethod_thenMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);

        assertTrue(employeeNode.attemptMark(employee, false));
        assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenDifferentObjectReference_whenUsingAttemptMarkMethod_thenMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee expectedEmployee = new Employee(123, "Mike");

        assertFalse(employeeNode.attemptMark(expectedEmployee, false));
        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertTrue(employeeNode.compareAndSet(employee, newEmployee, true, false));
        assertEquals(newEmployee, employeeNode.getReference());
        assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertFalse(employeeNode.compareAndSet(new Employee(1234, "Steve"), newEmployee, true, false));
        assertEquals(employee, employeeNode.getReference());
        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndNotCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertFalse(employeeNode.compareAndSet(employee, newEmployee, false, true));
        assertEquals(employee, employeeNode.getReference());
        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndNotCurrentMark_whenUsingCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertFalse(employeeNode.compareAndSet(new Employee(1234, "Steve"), newEmployee, false, true));
        assertEquals(employee, employeeNode.getReference());
        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertTrue(employeeNode.weakCompareAndSet(employee, newEmployee, true, false));
        assertEquals(newEmployee, employeeNode.getReference());
        assertFalse(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertFalse(employeeNode.weakCompareAndSet(new Employee(1234, "Steve"), newEmployee, true, false));
        assertEquals(employee, employeeNode.getReference());
        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenCurrentReferenceAndNotCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertFalse(employeeNode.weakCompareAndSet(employee, newEmployee, false, true));
        assertEquals(employee, employeeNode.getReference());
        assertTrue(employeeNode.isMarked());
    }

    @Test
    void givenNotCurrentReferenceAndNotCurrentMark_whenUsingWeakCompareAndSet_thenReferenceAndMarkShouldNotBeUpdated() {
        Employee employee = new Employee(123, "Mike");
        AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<Employee>(employee, true);
        Employee newEmployee = new Employee(124, "John");

        assertFalse(employeeNode.weakCompareAndSet(new Employee(1234, "Steve"), newEmployee, false, true));
        assertEquals(employee, employeeNode.getReference());
        assertTrue(employeeNode.isMarked());
    }

    @Data
    class Employee {
        private int id;
        private String name;

        Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
