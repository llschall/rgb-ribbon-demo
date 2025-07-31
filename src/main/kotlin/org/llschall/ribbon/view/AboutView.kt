package org.llschall.ribbon.view

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout

class AboutView : JPanel(BorderLayout()) {
    init {
        val url = "https://github.com/llschall/rgb-ribbon"
        val label = JLabel("<html>About: RGB Ribbon App<br><a href='" + url + "'>" + url + "</a></html>", JLabel.CENTER)
        label.cursor = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR)
        label.toolTipText = url
        label.addMouseListener(object : java.awt.event.MouseAdapter() {
            override fun mouseClicked(e: java.awt.event.MouseEvent?) {
                if (e != null && e.button == java.awt.event.MouseEvent.BUTTON1) {
                    try {
                        if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                            java.awt.Desktop.getDesktop().browse(java.net.URI(url))
                        }
                    } catch (ex: Exception) {
                        javax.swing.JOptionPane.showMessageDialog(label, "Could not open browser: " + ex.message)
                    }
                }
            }
        })
        add(label, BorderLayout.CENTER)
    }
}
