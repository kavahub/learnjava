package io.github.kavahub.learnjava.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * {@link Process} 示例
 * 
 * <p>
 * 运行情况说明：
 * <ul>
 * <li> 使用IDE单独运行每个测试，与Maven运行测试的结果不同 </li>
 * <li> Maven运行测试偶尔会失败 </li>
 * <li> 而且测试失败后，创建的JVM线程未关闭（已解决） </li>
 * <li> Maven运行测试失败后，测试无响应（已解决） </li>
 * </ul>
 * 
 * <p>
 * mvn test -Dtest=ProcessApiTest
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ProcessApiManualTest {
    private static List<Process> childProcess = new ArrayList<>();
    
    @AfterAll
    public static void afterAll() {
        childProcess.stream().filter(Objects::nonNull).forEach(p -> {
            if (p.isAlive()) {
                p.destroy();
            }
           
            if (p.isAlive()) {
                System.out.println("Process destroy forcibly!");
                p.destroyForcibly();
            }
        });
    }

    @Test
    public void processInfoExample() throws NoSuchAlgorithmException {
        ProcessHandle self = ProcessHandle.current();
        long PID = self.pid();
        ProcessHandle.Info procInfo = self.info();
        Optional<String[]> args = procInfo.arguments();
        Optional<String> cmd = procInfo.commandLine();
        Optional<Instant> startTime = procInfo.startInstant();
        Optional<Duration> cpuUsage = procInfo.totalCpuDuration();

        waistCPU();
        System.out.println("PID: " + PID);
        System.out.println("Args: " + args);
        System.out.println("Command: " + cmd.orElse("EmptyCmd"));
        System.out.println("Start time: " + startTime.get().toString());
        System.out.println(cpuUsage.get().toMillis());

        Stream<ProcessHandle> allProc = ProcessHandle.current().children();
        assertEquals(1, allProc.count());

        // Stream 已经关闭，重新获取
        allProc = ProcessHandle.current().children();
        allProc.forEach(p -> {
            System.out.println("Proc " + p.pid());
        });
        
    }

    @Test
    public void createAndDestroyProcess() throws IOException, InterruptedException {
        int numberOfChildProcesses = 5;
        for (int i = 0; i < numberOfChildProcesses; i++) {
            createNewJVM(ServiceMain.class, i).pid();
        }

        Thread.sleep(5000);
        
        Stream<ProcessHandle> childProc = ProcessHandle.current().children();
        // 本身也算一个
        assertEquals(numberOfChildProcesses + 1, childProc.count());

        childProc = ProcessHandle.current().children();
        childProc.forEach(processHandle -> {
            // ServiceMain的main函数会一直运行
            assertTrue(processHandle.isAlive(), "Process " + processHandle.pid() + " should be alive!");
            // 子线程退出事件
            CompletableFuture<ProcessHandle> onProcExit = processHandle.onExit();
            onProcExit.thenAccept(procHandle -> {
                System.out.println("Process with PID " + procHandle.pid() + " has stopped");
            });
        });

        Thread.sleep(10000);

        childProc = ProcessHandle.current().children();
        childProc.forEach(procHandle -> {
            // 触发子线程退出事件
            assertTrue(procHandle.destroy(), "Could not kill process " + procHandle.pid());
        });

        Thread.sleep(5000);

        childProc = ProcessHandle.current().children();
        childProc.forEach(procHandle -> {
            assertFalse(procHandle.isAlive(), "Process " + procHandle.pid() + " should not be alive!");
        });

    }

    private Process createNewJVM(Class<?> mainClass, int number) throws IOException {
        ArrayList<String> cmdParams = new ArrayList<String>(5);
        cmdParams.add(Processes.getJavaCmd().getAbsolutePath());
        cmdParams.add("-cp");
        cmdParams.add(Processes.getClassPath());
        cmdParams.add(mainClass.getName());
        // 给main函数传递参数
        cmdParams.add("Service " + number);
        ProcessBuilder myService = new ProcessBuilder(cmdParams);
        myService.inheritIO();
        Process process = myService.start();

        childProcess.add(process);
        return process;
    }

    private void waistCPU() throws NoSuchAlgorithmException {
        ArrayList<Integer> randArr = new ArrayList<Integer>(4096);
        SecureRandom sr = SecureRandom.getInstanceStrong();
        Duration somecpu = Duration.ofMillis(4200L);
        Instant end = Instant.now().plus(somecpu);
        while (Instant.now().isBefore(end)) {
            // System.out.println(sr.nextInt());
            randArr.add(sr.nextInt());
        }
    }  
    
    public static class ServiceMain {

        public static void main(String[] args) throws InterruptedException {
            ProcessHandle thisProcess = ProcessHandle.current();
            long pid = thisProcess.pid();
    
            Optional<String[]> opArgs = Optional.ofNullable(args);
            String procName = opArgs.map(str -> str.length > 0 ? str[0] : null).orElse(System.getProperty("sun.java.command"));
    
            // 一直运行
            while (true) {
                Thread.sleep(10000);
                System.out.println("Process " + procName + " with ID " + pid + " is running!");
            }
    
        }
    
    }
}
