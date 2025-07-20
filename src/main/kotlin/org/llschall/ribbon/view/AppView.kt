package org.llschall.ribbon.view

import org.llschall.ribbon.model.AppModel
import org.llschall.ribbon.controller.AppController
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import java.awt.BorderLayout
import javax.swing.JColorChooser

class MainPanel(private val model: AppModel, private val controller: AppController) : JPanel(BorderLayout()) {
    private val label = JLabel("Featuring ardwloop " + model.version, JLabel.CENTER)
    private val startButton = JButton("Start")
    private val toggleLedButton = JButton("Toggle build-in LED")
    private val colorChooser = JColorChooser(background).apply {
        previewPanel = JPanel() // Remove the color preview
    }
    private val buttonPanel = JPanel()
    private val rgbLabel = JLabel("RGB: ${colorToString(background)}", JLabel.CENTER)
    private val exitButton = JButton("Exit")

    init {
        startButton.addActionListener { controller.start() }
        colorChooser.selectionModel.addChangeListener {
            val c = colorChooser.color
            background = c // Set the panel background to the chosen color
            rgbLabel.text = "RGB: ${colorToString(c)}"
            model.ardwProgram?.let {
                it.red.set(c.red)
                it.green.set(c.green)
                it.blue.set(c.blue)
            }
        }
        toggleLedButton.addActionListener { controller.toggleBuiltInLed() }
        exitButton.addActionListener { System.exit(0) }
        buttonPanel.add(startButton)
        buttonPanel.add(toggleLedButton)
        buttonPanel.add(exitButton)
        add(colorChooser, BorderLayout.NORTH)
        add(label, BorderLayout.CENTER)
        add(rgbLabel, BorderLayout.EAST)
        add(buttonPanel, BorderLayout.SOUTH)
    }

    private fun colorToString(color: java.awt.Color): String {
        return "${color.red}, ${color.green}, ${color.blue}"
    }
}

class AppView(private val model: AppModel, private val controller: AppController) {
    fun show() {
        SwingUtilities.invokeLater {
            val frame = JFrame("RGB Ribbon")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.setSize(400, 500) // Increased height from 200 to 500
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
