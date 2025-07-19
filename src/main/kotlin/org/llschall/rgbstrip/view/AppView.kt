package org.llschall.rgbstrip.view

import org.llschall.rgbstrip.model.AppModel
import org.llschall.rgbstrip.controller.AppController
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import java.awt.BorderLayout

class AppView(private val model: AppModel, private val controller: AppController) {
    fun show() {
        SwingUtilities.invokeLater {
            val frame = JFrame("RGB Strip App (MVC)")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.setSize(400, 200)
            val label = JLabel("Featuring ardwloop " + model.version, JLabel.CENTER)
            val startButton = JButton("Start")
            startButton.addActionListener { controller.start() }
            val panel = JPanel(BorderLayout())
            panel.add(label, BorderLayout.CENTER)
            panel.add(startButton, BorderLayout.SOUTH)
            frame.add(panel)
            frame.setLocationRelativeTo(null)
            frame.isVisible = true
            frame.rootPane.registerKeyboardAction(
                { frame.dispose() },
                javax.swing.KeyStroke.getKeyStroke("ESCAPE"),
                javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW
            )
            frame.rootPane.registerKeyboardAction(
                { controller.start() },
                javax.swing.KeyStroke.getKeyStroke("SPACE"),
                javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW
            )
        }
    }
}
