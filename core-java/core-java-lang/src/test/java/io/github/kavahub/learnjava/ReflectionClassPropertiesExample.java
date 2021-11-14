package io.github.kavahub.learnjava;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.beanutils.PropertyUtils;

import lombok.Getter;
import lombok.Setter;

public class ReflectionClassPropertiesExample {
    public static void main(String[] args) {

        System.out.println("Fields for EmployeePojo are:");
        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(EmployeePojo.class)) {
            System.out.println(pd.getDisplayName());
        }

        System.out.println("Fields for EmployeeBean are:");
        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(EmployeeBean.class)) {
            System.out.println(pd.getDisplayName());
        }

    }   
    
    public static class EmployeePojo {

        public String firstName;
    
        public String lastName;
    
        private LocalDate startDate;
    
        public EmployeePojo(String firstName, String lastName, LocalDate startDate) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.startDate = startDate;
        }
    
        public String name() {
            return this.firstName + " " + this.lastName;
        }
    
        public LocalDate getStart() {
            return this.startDate;
        }
    
    }

    @Getter
    @Setter
    public class EmployeeBean implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -3760445487636086034L;
    
        private String firstName;
    
        private String lastName;
    
        private LocalDate startDate;
    
        public EmployeeBean() {
    
        }
    
        public EmployeeBean(String firstName, String lastName, LocalDate startDate) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.startDate = startDate;
        }
    
    
    }
}
