package io.github.kavahub.learnjava;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadDumpHelper {
    public void threadDump(String filename, boolean lockedMonitors, boolean lockedSynchronizers) throws IOException {
        Path threadDumpFile = Paths.get(filename);
        
        StringBuffer threadDump = new StringBuffer(System.lineSeparator());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for(ThreadInfo threadInfo : threadMXBean.dumpAllThreads(lockedMonitors, lockedSynchronizers)) {
            threadDump.append(threadInfo.toString());
        }
        Files.write(threadDumpFile, threadDump.toString().getBytes());
    }    
}
