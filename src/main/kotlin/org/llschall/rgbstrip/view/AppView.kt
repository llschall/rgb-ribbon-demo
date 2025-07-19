package org.llschall.rgbstrip.view

import org.llschall.rgbstrip.model.AppModel
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities

class AppView(private val model: AppModel) {
    fun show() {
        SwingUtilities.invokeLater {
            val frame = JFrame("RGB Strip App (MVC)")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.setSize(400, 200)
            val label = JLabel("Featuring ardwloop " + model.version, JLabel.CENTER)
            frame.add(label)
            frame.setLocationRelativeTo(null)
            frame.isVisible = true
        }
    }
}

