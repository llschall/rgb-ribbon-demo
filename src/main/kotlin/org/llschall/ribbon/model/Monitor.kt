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
            val cpuLoad = osBean.systemCpuLoad
            if (cpuLoad.isNaN() || cpuLoad < 0) 0.0 else cpuLoad
        } catch (_: Exception) {
            0.0
        }
    }


    fun getMgtCpu(): Double {
        val mbean = java.lang.management.ManagementFactory.getOperatingSystemMXBean()
        return try {
            val method = mbean.javaClass.getMethod("getSystemCpuLoad")
            val value = method.invoke(mbean) as? Double ?: -1.0
            if (value < 0) 0.0 else value
        } catch (_: Exception) {
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
        return 100*usedMemGB/totalMem
    }

}
