package io.github.kavahub.learnjava.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link Process} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class ProcessUnderstanding {
    public int compileAndRunJavaProgram() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("javac -cp src src\\main\\java\\io\\github\\kavahub\\learnjava\\process\\ProcessOutStream.java")
                // 等待编译完成
                .waitFor();

        Process process = Runtime.getRuntime().exec("java -cp  src/main/java io.github.kavahub.learnjavaprocess.ProcessOutStream");
        process.waitFor();

        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        return value;
    }

    public String getErrorStreamExample() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime()
                .exec("javac -cp src src\\main\\java\\io\\github\\kavahub\\learnjava\\process\\ProcessCompilationError.java1");
        process.waitFor();

        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        return errorString;
    }

    public void creatingNewProcess() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        builder.start();
    }

    public int filterProcessWithStreamsInSpecificRangeReturnCount() {
        return (int) ProcessHandle.allProcesses().filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000)).count();
    }

    public void destroyingProcessCreatedBySameProcess() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
    }

    public void destroyingProcessCreatedByDifferentProcess() {
        // find out the process id of current running task by checking
        // task manager in windows and enter the integer value
        Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(5232);
        ProcessHandle processHandle = optionalProcessHandle.get();
        processHandle.destroy();
    }

    public int waitForExample() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        return process.waitFor();
    }

    public int exitValueExample() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        process.destroy();
        return process.exitValue();
    }

    public void destroyExample() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
    }

    public void destroyForciblyExample() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }

    public int outputStreamDemo() throws IOException, InterruptedException {
        Runtime.getRuntime()
                .exec("javac -cp src src/main/java/io/github/kavahub/learnjava/process/ChildProcess.java "
                        .replace("/", File.separator))
                .waitFor(5, TimeUnit.SECONDS);

        Process process = Runtime.getRuntime()
                .exec("java -cp src/main/java io.github.kavahub.learnjava.process.ChildProcess"
                .replace("/", File.separator));

        // 发送消息
        try (Writer w = new OutputStreamWriter(process.getOutputStream(), "UTF-8")) {
            w.write("send to child\n");
        }
        

        new Thread(() -> {
            try {
                int c;
                while ((c = process.getInputStream().read()) != -1) {
                    System.out.write((byte) c);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // send to child

        return process.waitFor();
    }

}
