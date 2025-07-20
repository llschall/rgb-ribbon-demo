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
    private val greenButton = JButton("Green Background")
    private val colorChooser = JColorChooser(background).apply {
        // Only show the RGB tab
        val chooserPanels = this.chooserPanels
        for (panel in chooserPanels) {
            if (panel.displayName != "RGB") {
                this.removeChooserPanel(panel)
            }
        }
        this.previewPanel = JPanel() // Remove the sample text
    }
    private val buttonPanel = JPanel()
    private val rgbLabel = JLabel("RGB: ${colorToString(background)}", JLabel.CENTER)

    init {
        startButton.addActionListener { controller.start() }
        greenButton.addActionListener { background = java.awt.Color(0, 255, 0) }
        colorChooser.selectionModel.addChangeListener {
            background = colorChooser.color
            rgbLabel.text = "RGB: ${colorToString(colorChooser.color)}"
        }
        buttonPanel.add(startButton)
        buttonPanel.add(greenButton)
        add(label, BorderLayout.CENTER)
        add(buttonPanel, BorderLayout.SOUTH)
        add(colorChooser, BorderLayout.NORTH)
        add(rgbLabel, BorderLayout.WEST)
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
