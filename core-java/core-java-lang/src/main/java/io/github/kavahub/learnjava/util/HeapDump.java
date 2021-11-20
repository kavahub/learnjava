package io.github.kavahub.learnjava.util;

import com.sun.management.HotSpotDiagnosticMXBean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;

import javax.management.MBeanServer;

import lombok.experimental.UtilityClass;

/**
 * 堆转储到文件
 */
@UtilityClass
public class HeapDump {
    public void dumpHeap(Path filePath, boolean live) throws IOException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(server,
                "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
        mxBean.dumpHeap(filePath.toAbsolutePath().toString(), live);
    }
}
