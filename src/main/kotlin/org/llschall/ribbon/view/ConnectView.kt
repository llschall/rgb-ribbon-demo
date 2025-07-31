package org.llschall.ribbon.view

import org.llschall.ribbon.model.AppModel
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.JColorChooser
import javax.swing.Timer

class ConnectView(
    label: JLabel,
    colorChooser: JColorChooser,
    private val model: AppModel
) : JPanel(BorderLayout()) {

    val cpuLabel: JLabel = JLabel()
    val memLabel: JLabel = JLabel()

    init {
        add(label, BorderLayout.CENTER)
        add(cpuLabel, BorderLayout.WEST)
        add(memLabel, BorderLayout.SOUTH)
        add(colorChooser, BorderLayout.NORTH)
        // The buttonPanel is always added in AppView, not here

        // Timer to update CPU and memory usage every second
        Timer(1000) {

            cpuLabel.text = "CPU Usage: %.2f%%".format(model.monitor.getOshiCpu())
            memLabel.text = "Memory Usage: %.2f%%".format(model.monitor.getOshiMem())
        }.start()
    }
}
