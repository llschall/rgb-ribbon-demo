package org.llschall.ribbon.view

import org.llschall.ribbon.model.AppModel
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.Timer
import java.awt.Color

class MonitorView(private val model: AppModel) : JPanel(BorderLayout()) {
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
            val cpu = model.monitor.getCpuLoad()
            // Scale cpu in the range of 0 to 255
            val scaled = (cpu * 255).toInt()
            cpuLbl.text = "CPU Usage: $cpu $scaled"
            redLbl.background = Color(scaled,0,0);
        }
        timer.start()
    }

    fun setStartAction(action: () -> Unit) {
        monitorBtn.addActionListener { action() }
    }
}
