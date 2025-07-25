package org.llschall.ribbon.view

import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.Timer
import java.awt.Color

class MonitorView : JPanel(BorderLayout()) {
    private val cpuLbl = JLabel("CPU Usage: --%", JLabel.CENTER)
    private val monitorBtn = JButton("Monitor")
    private val redLbl = JLabel("Ribbon", JLabel.CENTER).apply { foreground = Color.RED }

    init {
        redLbl.isOpaque = true
        redLbl.background = Color.WHITE
        add(cpuLbl, BorderLayout.CENTER)
        add(monitorBtn, BorderLayout.NORTH)
        add(redLbl, BorderLayout.SOUTH)
        val timer = Timer(1000) {
            val cpu = getCpuLoad()
            println(cpu)
            // Scale cpu in the range of 0 to 255
            val scaled = (cpu * 255).toInt()
            cpuLbl.text = "CPU Usage: $cpu $scaled"
            redLbl.background = Color(scaled,0,0);
        }
        timer.start()
    }

    private fun getCpuLoad(): Double {
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

    fun setStartAction(action: () -> Unit) {
        monitorBtn.addActionListener { action() }
    }
}
