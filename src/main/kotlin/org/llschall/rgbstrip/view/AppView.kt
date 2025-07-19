package org.llschall.rgbstrip.view

import org.llschall.rgbstrip.model.AppModel
import org.llschall.rgbstrip.controller.AppController
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import java.awt.BorderLayout

class MainPanel(private val model: AppModel, private val controller: AppController) : JPanel(BorderLayout()) {
    private val label = JLabel("Featuring ardwloop " + model.version, JLabel.CENTER)
    private val startButton = JButton("Start")
    private val greenButton = JButton("Green Background")
    private val buttonPanel = JPanel()

    init {
        startButton.addActionListener { controller.start() }
        greenButton.addActionListener { background = java.awt.Color(0, 255, 0) }
        buttonPanel.add(startButton)
        buttonPanel.add(greenButton)
        add(label, BorderLayout.CENTER)
        add(buttonPanel, BorderLayout.SOUTH)
    }
}

class AppView(private val model: AppModel, private val controller: AppController) {
    fun show() {
        SwingUtilities.invokeLater {
            val frame = JFrame("RGB Strip App (MVC)")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.setSize(400, 200)
            val panel = MainPanel(model, controller)
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
