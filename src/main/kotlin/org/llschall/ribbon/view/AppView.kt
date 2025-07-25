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
import javax.swing.JScrollPane
import javax.swing.JList
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionListener
import javax.swing.JSplitPane
import org.llschall.ribbon.view.MonitorView
import java.awt.image.BufferedImage
import javax.swing.ImageIcon

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
    private val cpuLabel = JLabel("CPU Usage: --%", JLabel.CENTER)
    private val viewNames = arrayOf("Connect", "Monitor", "About")
    private val viewList = JList(viewNames).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
        selectedIndex = 1 // Show Monitor view by default
    }
    private val mainContentPanel = JPanel(BorderLayout())

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
        // Setup mainContentPanel as the switchable view area
        mainContentPanel.add(label, BorderLayout.CENTER)
        mainContentPanel.add(rgbLabel, BorderLayout.EAST)
        mainContentPanel.add(cpuLabel, BorderLayout.WEST)
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
                        mainContentPanel.add(ConnectView(label, rgbLabel, cpuLabel, buttonPanel, colorChooser), BorderLayout.CENTER)
                    }
                    "Monitor" -> {
                        val monitorView = MonitorView()
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
        val timer = javax.swing.Timer(1000) {
            val cpuLoad = getCpuLoad()
            cpuLabel.text = "CPU Usage: ${String.format("%.2f", cpuLoad * 100)}%"
        }
        timer.start()
    }

    private fun colorToString(color: java.awt.Color): String {
        return "${color.red}, ${color.green}, ${color.blue}"
    }

    private fun getCpuLoad(): Double {
        val mbean = java.lang.management.ManagementFactory.getOperatingSystemMXBean()
        return try {
            val method = mbean.javaClass.getMethod("getSystemCpuLoad")
            val value = method.invoke(mbean) as? Double ?: -1.0
            if (value < 0) 0.0 else value
        } catch (e: Exception) {
            0.0
        }
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
