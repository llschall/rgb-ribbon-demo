package org.llschall.ribbon.view

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout

class ConnectView(
    val label: JLabel,
    val rgbLabel: JLabel,
    val cpuLabel: JLabel,
    val buttonPanel: JPanel,
    val colorChooser: javax.swing.JColorChooser
) : JPanel(BorderLayout()) {
    init {
        add(label, BorderLayout.CENTER)
        add(rgbLabel, BorderLayout.EAST)
        add(cpuLabel, BorderLayout.WEST)
        add(colorChooser, BorderLayout.NORTH)
        // The buttonPanel is always added in AppView, not here
    }
}

