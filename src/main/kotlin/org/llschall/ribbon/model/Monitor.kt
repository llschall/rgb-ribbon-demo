package org.llschall.ribbon.model
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory
import kotlin.text.toDouble
import kotlin.times

class Monitor {
    private val processor: CentralProcessor = SystemInfo().hardware.processor
    private val memory: GlobalMemory = SystemInfo().hardware.memory
    private var prevTicks: LongArray = processor.systemCpuLoadTicks

    fun getCpuLoad(): Double {
        val osBean = java.lang.management.ManagementFactory.getPlatformMXBean(
            com.sun.management.OperatingSystemMXBean::class.java
        )
        return try {
            val cpuLoad = osBean.systemCpuLoad
            if (cpuLoad.isNaN() || cpuLoad < 0) 0.0 else cpuLoad
        } catch (e: Exception) {
            0.0
        }
    }

    fun getOshiCpu(): Double {
        val ticks = processor.systemCpuLoadTicks
        val cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100.0
        prevTicks = ticks
        return cpuLoad
    }

    fun getOshiMem(): Double {
        val totalMem = memory.total
        val availMem = memory.available
        val usedMem = totalMem - availMem
        val usedMemGB = usedMem.toDouble() / (1024 * 1024 * 1024)
        val totalMemGB = totalMem.toDouble() / (1024 * 1024 * 1024)
        return 100*usedMemGB/totalMem;
    }
}
