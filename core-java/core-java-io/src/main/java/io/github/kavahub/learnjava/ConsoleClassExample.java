package io.github.kavahub.learnjava;

import java.io.Console;

/**
 * {@link Console} 示例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class ConsoleClassExample {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.print("No console available");
            return;
        }

        String progLanguauge = console.readLine("Enter your favourite programming language: ");
        console.printf(progLanguauge + " is very interesting!");

        char[] pass = console.readPassword("To finish, enter password: ");
        
        if ("BAELDUNG".equals(pass.toString().toUpperCase()))
            console.printf("Good! Regards!");
        else 
            console.printf("Nice try. Regards.");

    }   
}
