package io.github.kavahub.learnjava;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JvmExitAndHalt {
    static {
        Runtime.getRuntime()
            .addShutdownHook(new Thread(() -> {
                log.info("Shutdown hook initiated.");
            }));
    }

    public void processAndExit() {
        process();
        log.info("Calling System.exit().");
        System.exit(0);
    }

    public void processAndHalt() {
        process();
        log.info("Calling Runtime.getRuntime().halt().");
        Runtime.getRuntime()
            .halt(0);
    }

    private void process() {
        log.info("Process started.");
    }
 
}
