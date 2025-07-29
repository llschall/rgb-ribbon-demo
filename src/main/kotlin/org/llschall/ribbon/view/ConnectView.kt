package org.llschall.ribbon.view

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory
import java.awt.Component
import javax.swing.Timer

class ConnectView(
    label: JLabel,
    rgbLabel: JLabel,
    colorChooser: javax.swing.JColorChooser
) : JPanel(BorderLayout()) {


    val cpuLabel: JLabel = JLabel()
    val memLabel: JLabel = JLabel()
    private val processor: CentralProcessor = SystemInfo().hardware.processor
    private val memory: GlobalMemory = SystemInfo().hardware.memory
    private var prevTicks: LongArray = processor.systemCpuLoadTicks

    init {
        add(label, BorderLayout.CENTER)
        add(rgbLabel, BorderLayout.EAST)
        add(cpuLabel, BorderLayout.WEST)
        add(memLabel, BorderLayout.SOUTH)
        add(colorChooser, BorderLayout.NORTH)
        // The buttonPanel is always added in AppView, not here

        // Timer to update CPU and memory usage every second
        Timer(1000) {
            val ticks = processor.systemCpuLoadTicks
            val cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100.0
            cpuLabel.text = "CPU Usage: %.2f%%".format(cpuLoad)
            prevTicks = ticks

            val totalMem = memory.total
            val availMem = memory.available
            val usedMem = totalMem - availMem
            val usedMemGB = usedMem.toDouble() / (1024 * 1024 * 1024)
            val totalMemGB = totalMem.toDouble() / (1024 * 1024 * 1024)
            memLabel.text = "Memory Usage: %.2f GB / %.2f GB".format(usedMemGB, totalMemGB)
        }.start()
    }
}
