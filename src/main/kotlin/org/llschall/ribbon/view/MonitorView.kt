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
    private val redLbl = JLabel("Red Label", JLabel.CENTER).apply { foreground = Color.RED }

    init {
        add(cpuLbl, BorderLayout.CENTER)
        add(monitorBtn, BorderLayout.SOUTH)
        add(redLbl, BorderLayout.NORTH)
        val timer = Timer(1000) {
            val cpuLoad = getCpuLoad()
            cpuLbl.text = "CPU Usage: $cpuLoad"
        }
        timer.start()
    }

    private fun getCpuLoad(): Double {
        val mbean = java.lang.management.ManagementFactory.getOperatingSystemMXBean()
        return try {
            val method = mbean.javaClass.getMethod("getSystemCpuLoad")
            val value = method.invoke(mbean) as? Double ?: -1.0
            if (value < 0) 0.0 else value
        } catch (e: Exception) {
            0.0
        }
    }

    fun setStartAction(action: () -> Unit) {
        monitorBtn.addActionListener { action() }
    }
}
