package io.github.kavahub.learnjava.process;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessAPIEnhancementsExample {
  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
    infoOfCurrentProcess();
    infoOfLiveProcesses();
    infoOfSpawnProcess();
    infoOfExitCallback();
    infoOfChildProcess();
  }

  private static void infoOfCurrentProcess() {
    log.info("**************** {} ****************", "infoOfCurrentProcess");

    ProcessHandle processHandle = ProcessHandle.current();
    ProcessHandle.Info processInfo = processHandle.info();

    log.info("PID: " + processHandle.pid());
    log.info("Arguments: " + processInfo.arguments());
    log.info("Command: " + processInfo.command());
    log.info("Instant: " + processInfo.startInstant());
    log.info("Total CPU duration: " + processInfo.totalCpuDuration());
    log.info("User: " + processInfo.user());
  }

  private static void infoOfSpawnProcess() throws IOException {
    log.info("**************** {} ****************", "infoOfSpawnProcess");

    String javaCmd = Processes.getJavaCmd().getAbsolutePath();
    ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
    Process process = processBuilder.inheritIO().start();
    ProcessHandle processHandle = process.toHandle();
    ProcessHandle.Info processInfo = processHandle.info();

    log.info("PID: " + processHandle.pid());
    log.info("Arguments: " + processInfo.arguments());
    log.info("Command: " + processInfo.command());
    log.info("Instant: " + processInfo.startInstant());
    log.info("Total CPU duration: " + processInfo.totalCpuDuration());
    log.info("User: " + processInfo.user());
  }

  private static void infoOfLiveProcesses() {
    log.info("**************** {} ****************", "infoOfLiveProcesses");

    Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
    liveProcesses.filter(ProcessHandle::isAlive).forEach(ph -> {
      log.info("PID: " + ph.pid());
      log.info("Instance: " + ph.info().startInstant());
      log.info("User: " + ph.info().user());
    });
  }

  private static void infoOfChildProcess() throws IOException {
    log.info("**************** {} ****************", "infoOfChildProcess");

    int childProcessCount = 5;
    for (int i = 0; i < childProcessCount; i++) {
      String javaCmd = Processes.getJavaCmd().getAbsolutePath();
      ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
      processBuilder.inheritIO().start();
    }

    Stream<ProcessHandle> children = ProcessHandle.current().children();
    children.filter(ProcessHandle::isAlive).forEach(ph -> log.info("PID: {}, Cmd: {}", ph.pid(), ph.info().command()));
    Stream<ProcessHandle> descendants = ProcessHandle.current().descendants();
    descendants.filter(ProcessHandle::isAlive)
        .forEach(ph -> log.info("PID: {}, Cmd: {}", ph.pid(), ph.info().command()));
  }

  private static void infoOfExitCallback() throws IOException, InterruptedException, ExecutionException {
    log.info("**************** {} ****************", "infoOfExitCallback");

    String javaCmd = Processes.getJavaCmd().getAbsolutePath();
    ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
    Process process = processBuilder.inheritIO().start();
    ProcessHandle processHandle = process.toHandle();

    log.info("PID: {} has started", processHandle.pid());
    CompletableFuture<ProcessHandle> onProcessExit = processHandle.onExit();
    onProcessExit.get();
    log.info("Alive: " + processHandle.isAlive());
    onProcessExit.thenAccept(ph -> {
      log.info("PID: {} has stopped", ph.pid());
    });
  }

}
