package io.github.kavahub.learnjava.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.experimental.UtilityClass;

/** 
 * 线程转储
 * 
 */
@UtilityClass
public class ThreadDumpHelper {
    public void threadDump(Path filename, boolean lockedMonitors, boolean lockedSynchronizers) throws IOException {
       
        StringBuffer threadDump = new StringBuffer(System.lineSeparator());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for(ThreadInfo threadInfo : threadMXBean.dumpAllThreads(lockedMonitors, lockedSynchronizers)) {
            threadDump.append(threadInfo.toString());
        }
        Files.write(filename, threadDump.toString().getBytes());
    }    
}
