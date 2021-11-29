package io.github.kavahub.learnjava.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * 
 * {@link ProcessUnderstanding} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ProcessUnderstandingTest {
    @Test
    public void givenSubProcess_whenEncounteringError_thenErrorStreamNotNull() throws IOException {
        Process process = Runtime.getRuntime()
                .exec("javac -cp src src\\main\\java\\net\\learnjava\\process\\ProcessCompilationError.java1");
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        assertNotNull(errorString);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenStarted_thenStartSuccessIsAlive() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertTrue(process.isAlive());

        TimeUnit.SECONDS.sleep(1);
        process.destroy();
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenDestroying_thenProcessNotAlive() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        TimeUnit.SECONDS.sleep(1);

        process.destroy();
        assertFalse(process.isAlive());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenAlive_thenDestroyForcibly() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        TimeUnit.SECONDS.sleep(1);

        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
        assertFalse(process.isAlive());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenDestroyed_thenCheckIfAlive() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        TimeUnit.SECONDS.sleep(1);

        process.destroy();
        assertFalse(process.isAlive());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenCurrentThreadWaitsIndefinitelyuntilSubProcessEnds_thenProcessWaitForReturnsGrt0()
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertFalse(process.waitFor(1, TimeUnit.SECONDS));

        TimeUnit.SECONDS.sleep(1);
        process.destroy();
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenCurrentThreadWaitsAndSubProcessNotTerminated_thenProcessWaitForReturnsFalse()
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertFalse(process.waitFor(1, TimeUnit.SECONDS));

        TimeUnit.SECONDS.sleep(1);
        process.destroy();
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void givenSubProcess_whenCurrentThreadWillNotWaitIndefinitelyforSubProcessToEnd_thenProcessExitValueReturnsGrt0()
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();

        TimeUnit.SECONDS.sleep(1);
        process.destroy();

        assertTrue(process.exitValue() >= 0);
    }

    @Test
    public void givenRunningProcesses_whenFilterOnProcessIdRange_thenGetSelectedProcessPid() {
        assertTrue(
                ((int) ProcessHandle.allProcesses().filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000)).count()) > 0);
    }

    @Test
    public void givenSourceProgram_whenReadingInputStream_thenFirstLineEquals3()
            throws IOException, InterruptedException {

        Runtime.getRuntime().exec(
                "javac -encoding UTF-8 -cp src src/main/java/io/github/kavahub/learnjava/process/ProcessOutStream.java".replace("/", File.separator))
                .waitFor(5, TimeUnit.SECONDS);

        Process process = Runtime.getRuntime()
                .exec("java -cp src/main/java io.github.kavahub.learnjava.process.ProcessOutStream".replace("/", File.separator));

        process.waitFor(5, TimeUnit.SECONDS);

        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = output.readLine();
        int value = Integer.parseInt(line);

        assertEquals(3, value);
    }

    @Test
    public void outputStreamDemoTest() throws IOException, InterruptedException {
        int code = ProcessUnderstanding.outputStreamDemo();
        assertEquals(0, code);
    }
}
