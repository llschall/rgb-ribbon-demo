package org.llschall.ribbon.view

import org.llschall.ribbon.model.AppModel
import org.llschall.ribbon.controller.AppController
import org.llschall.ribbon.model.Monitor
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import java.awt.BorderLayout
import javax.swing.JColorChooser
import javax.swing.JScrollPane
import javax.swing.JList
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionListener
import javax.swing.JSplitPane

class MainPanel(private val model: AppModel, private val controller: AppController) : JPanel(BorderLayout()) {
    private val label = JLabel("Featuring ardwloop " + model.version, JLabel.CENTER)
    private val startButton = JButton("Start")
    private val toggleLedButton = JButton("Toggle build-in LED")
    private val colorChooser = JColorChooser(background).apply {
        previewPanel = JPanel() // Remove the color preview
    }
    private val buttonPanel = JPanel()
    private val exitButton = JButton("Exit")
    private val viewNames = arrayOf("Monitor", "Connect", "About")
    private val viewList = JList(viewNames).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
        selectedIndex = 0 // Show Monitor view by default
    }
    private val mainContentPanel = JPanel(BorderLayout())

    init {
        startButton.addActionListener { controller.start() }
        colorChooser.selectionModel.addChangeListener {
            val c = colorChooser.color
            background = c // Set the panel background to the chosen color
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
        // Setup mainContentPanel as the switchable view area
        mainContentPanel.add(label, BorderLayout.CENTER)
        mainContentPanel.add(buttonPanel, BorderLayout.SOUTH)
        // Always add the button panel to the SOUTH of mainContentPanel
        mainContentPanel.add(buttonPanel, BorderLayout.SOUTH)
        // Listen for view changes
        viewList.addListSelectionListener(ListSelectionListener { e ->
            if (!e.valueIsAdjusting) {
                // Remove all except the button panel
                mainContentPanel.removeAll()
                when (viewList.selectedValue) {
                    "Connect" -> {
                        mainContentPanel.add(ConnectView(label, colorChooser, model),
                            BorderLayout.CENTER)
                    }
                    "Monitor" -> {
                        val monitorView = MonitorView(model)
                        monitorView.setStartAction { controller.start() }
                        mainContentPanel.add(monitorView, BorderLayout.CENTER)
                    }
                    "About" -> {
                        mainContentPanel.add(AboutView(), BorderLayout.CENTER)
                    }
                }
                // Always add the button panel after switching views
                mainContentPanel.add(buttonPanel, BorderLayout.SOUTH)
                mainContentPanel.revalidate()
                mainContentPanel.repaint()
            }
        })
        // Remove split pane and use only mainContentPanel
        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, JScrollPane(viewList), mainContentPanel)
        splitPane.dividerLocation = 150
        add(splitPane, BorderLayout.CENTER)
    }
}

class AppView(private val model: AppModel, private val controller: AppController) {
    fun show() {

        SwingUtilities.invokeLater {
            val frame = JFrame("RGB Ribbon")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.setSize(600, 500) // Changed width to 600, height remains 500
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
