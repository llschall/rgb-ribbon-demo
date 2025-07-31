package org.llschall.ribbon.model
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory

class Monitor {
    private val processor: CentralProcessor = SystemInfo().hardware.processor
    private val memory: GlobalMemory = SystemInfo().hardware.memory
    private var prevTicks: LongArray = processor.systemCpuLoadTicks

    fun getBeanCpu(): Double {
        val osBean = java.lang.management.ManagementFactory.getPlatformMXBean(
            com.sun.management.OperatingSystemMXBean::class.java
        )
        return try {
            val cpuLoad = osBean.cpuLoad
            if (cpuLoad.isNaN() || cpuLoad < 0) 0.0 else cpuLoad
        } catch (_: Exception) {
            0.0
        }
    }


    fun getMgtCpu(): Double {
        return 0.1234
    }

    fun getOshiCpu(): Double {
        val ticks = processor.systemCpuLoadTicks
        val cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks)
        prevTicks = ticks
        return cpuLoad
    }

    fun getOshiMem(): Double {
        val totalMem = memory.total.toDouble()
        val availMem = memory.available.toDouble()
        val usedMem = totalMem - availMem
        return usedMem / totalMem
    }

}
