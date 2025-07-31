package org.llschall.ribbon.view

import org.llschall.ribbon.model.AppModel
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.Timer
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout

class MonitorView(private val model: AppModel) : JPanel(BorderLayout()) {
    private val cpu1Lbl = JLabel("--", JLabel.CENTER)
    private val cpu2Lbl = JLabel("--", JLabel.CENTER)
    private val oshiLbl = JLabel("--", JLabel.CENTER)
    private val memLbl = JLabel("--", JLabel.CENTER)

    private val monitorBtn = JButton("Monitor")
    private val redLbl = JLabel("Ribbon", JLabel.CENTER).apply { foreground = Color.RED }

    init {
        redLbl.isOpaque = true
        redLbl.background = Color.WHITE

        val gridPanel = JPanel(GridLayout(0, 1,  ))
        gridPanel.add(cpu1Lbl)
        gridPanel.add(cpu2Lbl)
        gridPanel.add(oshiLbl)
        gridPanel.add(memLbl)

        // Align all labels to the right
        cpu1Lbl.horizontalAlignment = JLabel.RIGHT
        cpu2Lbl.horizontalAlignment = JLabel.RIGHT
        oshiLbl.horizontalAlignment = JLabel.RIGHT
        memLbl.horizontalAlignment = JLabel.RIGHT

        val panel = JPanel(FlowLayout())
        panel.add(gridPanel)

        add(panel, BorderLayout.CENTER)
        add(monitorBtn, BorderLayout.NORTH)
        add(redLbl, BorderLayout.SOUTH)
        val timer = Timer(1000) {
            val mem = model.monitor.getOshiMem()
            // Scale cpu in the range of 0 to 255
            val scaled = 100
            cpu1Lbl.text = "Bean CPU 1:  "+cpu2Str(model.monitor.getBeanCpu())
            cpu2Lbl.text = "Bean CPU 2:  "+cpu2Str(model.monitor.getMgtCpu())
            oshiLbl.text = "OSHI CPU:    "+cpu2Str(model.monitor.getOshiCpu())
            memLbl.text = "OSHI Mem: "+String.format("%.2f", mem)+" %"

            redLbl.background = Color(scaled,0,0);
        }
        timer.start()
    }

    fun cpu2Str(cpu: Double) : String {
        return String.format("%05.2f", cpu)+" %"
    }

    fun setStartAction(action: () -> Unit) {
        monitorBtn.addActionListener { action() }
    }
}
