package io.github.kavahub.learnjava.plugins.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface JVMInfo {
    static final long MB = 1048576L;

    static String getMemoryInfo() {
        StringBuilder rslt = new StringBuilder();

        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();

        String headMemoryInfo = String.format("\ninit: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                headMemory.getInit() / MB + "MB",
                headMemory.getMax() / MB + "MB", headMemory.getUsed() / MB + "MB",
                headMemory.getCommitted() / MB + "MB",
                headMemory.getUsed() * 100 / headMemory.getCommitted() + "%"

        );

        rslt.append(headMemoryInfo);

        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();

        String nonheadMemoryInfo = String.format("init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                nonheadMemory.getInit() / MB + "MB",
                nonheadMemory.getMax() / MB + "MB", nonheadMemory.getUsed() / MB + "MB",
                nonheadMemory.getCommitted() / MB + "MB",
                nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%"

        );
        
        rslt.append(nonheadMemoryInfo);
        return rslt.toString();
    }

    static String getGCInfo() {
        StringBuilder rslt = new StringBuilder();

        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbage.getName(),
                    garbage.getCollectionCount(),
                    garbage.getCollectionTime(),
                    Arrays.deepToString(garbage.getMemoryPoolNames()));
            rslt.append(info);
        }

        return rslt.toString();
    }
}
