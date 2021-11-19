package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.InputStreamToString;

/**
 * curl命令行测试
 * 
 */
public class JavaCurlIntegrateTest {
    private final static String USER_HOME = System.getProperty("user.home");
    @Test
    public void givenCommand_whenCalled_thenProduceZeroExitCode() throws IOException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File(USER_HOME));
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        // Consume the inputStream so the process can exit
        InputStreamToString.consumeInputStream(inputStream);
        int exitCode = process.exitValue();
        
        assertEquals(0, exitCode);
    }
    
    @Test
    public void givenNewCommands_whenCalled_thenCheckIfIsAlive() throws IOException {
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File(USER_HOME));
        Process process = processBuilder.start();
        
        // Re-use processBuilder
        processBuilder.command(new String[]{"newCommand", "arguments"});
        
        assertEquals(true, process.isAlive());
    }

    @Test
    public void whenRequestPost_thenCheckIfReturnContent() throws IOException {
        String command = "curl -X POST https://postman-echo.com/post --data foo1=bar1&foo2=bar2";
        Process process = Runtime.getRuntime().exec(command);
        
        // Get the POST result
        String content = InputStreamToString.inputStreamToString(process.getInputStream());

        assertTrue(null != content && !content.isEmpty());
    }    
}
