package org.llschall.ribbon.view

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import javax.swing.Timer

class ConnectView(
    label: JLabel,
    rgbLabel: JLabel,
    cpuLabel: JLabel,
    buttonPanel: JPanel,
    colorChooser: javax.swing.JColorChooser
) : JPanel(BorderLayout()) {
    private val processor: CentralProcessor = SystemInfo().hardware.processor
    private var prevTicks: LongArray = processor.systemCpuLoadTicks

    init {
        add(label, BorderLayout.CENTER)
        add(rgbLabel, BorderLayout.EAST)
        add(cpuLabel, BorderLayout.WEST)
        add(colorChooser, BorderLayout.NORTH)
        // The buttonPanel is always added in AppView, not here

        // Timer to update CPU usage every second
        Timer(1000) {
            val ticks = processor.systemCpuLoadTicks
            val cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100.0
            cpuLabel.text = "CPU Usage: %.2f%%".format(cpuLoad)
            prevTicks = ticks
        }.start()
    }
}
