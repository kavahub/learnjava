package io.github.kavahub.learnjava.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoubleColonTest {
    @Test
    public void testConstructorReference() {

        Computer c1 = new Computer(2015, "white");
        Computer c2 = new Computer(2009, "black");
        Computer c3 = new Computer(2014, "black");

        BiFunction<Integer, String, Computer> c4Function = Computer::new;
        Computer c4 = c4Function.apply(2013, "white");
        BiFunction<Integer, String, Computer> c5Function = Computer::new;
        Computer c5 = c5Function.apply(2010, "black");
        BiFunction<Integer, String, Computer> c6Function = Computer::new;
        Computer c6 = c6Function.apply(2008, "black");

        List<Computer> inventory = Arrays.asList(c1, c2, c3, c4, c5, c6);

        List<Computer> blackComputer = ComputerUtils.filter(inventory, ComputerUtils.blackPredicate);
        assertEquals(4, blackComputer.size(), "The black Computers are: ");

        List<Computer> after2010Computer = ComputerUtils.filter(inventory, ComputerUtils.after2010Predicate);
        assertEquals(3, after2010Computer.size(), "The Computer bought after 2010 are: ");

        List<Computer> before2011Computer = ComputerUtils.filter(inventory, c -> c.getAge() < 2011);
        assertEquals(3, before2011Computer.size(), "The Computer bought before 2011 are: ");

        inventory.sort(Comparator.comparing(Computer::getAge));

        assertEquals( c6, inventory.get(0), "Oldest Computer in inventory");

    }

    @Test
    public void testStaticMethodReference() {

        Computer c1 = new Computer(2015, "white", 35);
        Computer c2 = new Computer(2009, "black", 65);
        TriFunction<Integer, String, Integer, Computer> c6Function = Computer::new;
        Computer c3 = c6Function.apply(2008, "black", 90);

        List<Computer> inventory = Arrays.asList(c1, c2, c3);
        inventory.forEach(ComputerUtils::repair);

        assertEquals(Integer.valueOf(100), c1.getHealty(), "Computer repaired");
    }

    @Test
    public void testInstanceMethodArbitraryObjectParticularType() {

        Computer c1 = new Computer(2015, "white", 35);
        Computer c2 = new MacbookPro(2009, "black", 65);
        List<Computer> inventory = Arrays.asList(c1, c2);
        inventory.forEach(Computer::turnOnPc);

    }

    @Test
    public void testSuperMethodReference() {

        final TriFunction<Integer, String, Integer, MacbookPro> integerStringIntegerObjectTriFunction = MacbookPro::new;
        final MacbookPro macbookPro = integerStringIntegerObjectTriFunction.apply(2010, "black", 100);
        Double initialValue = 999.99;
        final Double actualValue = macbookPro.calculateValue(initialValue);
        assertEquals(766.659, actualValue, 0.0);
    }   

    @Getter
    @Setter
    public static class Computer {

        private Integer age;
        private String color;
        private Integer healty;
    
        Computer(final int age, final String color) {
            this.age = age;
            this.color = color;
        }
    
        Computer(final Integer age, final String color, final Integer healty) {
            this.age = age;
            this.color = color;
            this.healty = healty;
        }
    
        public Computer() {
        }
    
        public void turnOnPc() {
            System.out.println("Computer turned on");
        }
    
        public void turnOffPc() {
            System.out.println("Computer turned off");
        }
    
        public Double calculateValue(Double initialValue) {
            return initialValue / 1.50;
        }
    
        @Override
        public String toString() {
            return "Computer{" + "age=" + age + ", color='" + color + '\'' + ", healty=" + healty + '}';
        }
    
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
    
            final Computer computer = (Computer) o;
    
            return (age != null ? age.equals(computer.age) : computer.age == null) && (color != null ? color.equals(computer.color) : computer.color == null);
    
        }
    
        @Override
        public int hashCode() {
            int result = age != null ? age.hashCode() : 0;
            result = 31 * result + (color != null ? color.hashCode() : 0);
            return result;
        }
    }

    public class MacbookPro extends Computer {
        public MacbookPro(int age, String color) {
            super(age, color);
        }
    
        MacbookPro(Integer age, String color, Integer healty) {
            super(age, color, healty);
        }
    
        @Override
        public void turnOnPc() {
            log.debug("MacbookPro turned on");
        }
    
        @Override
        public void turnOffPc() {
            log.debug("MacbookPro turned off");
        }
    
        @Override
        public Double calculateValue(Double initialValue) {
            Function<Double, Double> function = super::calculateValue;
            final Double pcValue = function.apply(initialValue);
            log.debug("First value is:" + pcValue);
            return pcValue + (initialValue / 10);
    
        }
    }
    
    public static class ComputerUtils {

        static final ComputerPredicate after2010Predicate = (c) -> (c.getAge() > 2010);
        static final ComputerPredicate blackPredicate = (c) -> "black".equals(c.getColor());
    
        public static List<Computer> filter(final List<Computer> inventory, final ComputerPredicate p) {
    
            final List<Computer> result = new ArrayList<>();
            inventory.stream().filter(p::filter).forEach(result::add);
    
            return result;
        }
    
        static void repair(final Computer computer) {
            if (computer.getHealty() < 50) {
                computer.setHealty(100);
            }
        }
    
    }

    @FunctionalInterface
public static interface ComputerPredicate {

    boolean filter(Computer c);

}

@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (final A a, final B b, final C c) -> after.apply(apply(a, b, c));
    }
}
}
