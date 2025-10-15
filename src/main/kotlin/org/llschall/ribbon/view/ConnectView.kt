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

    private val slider = javax.swing.JSlider(0, 255, 55)

    init {
        add(label, BorderLayout.CENTER)
        add(colorChooser, BorderLayout.NORTH)
        add(slider, BorderLayout.SOUTH)

        slider.addChangeListener {
            model.ardwProgram?.brightness?.set(slider.value)
        }
    }
}
