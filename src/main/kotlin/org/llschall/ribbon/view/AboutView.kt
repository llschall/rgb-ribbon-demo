package org.llschall.ribbon.view

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout

class AboutView : JPanel(BorderLayout()) {
    init {
        add(JLabel("About: RGB Ribbon App\nhttps://github.com/llschall/rgb-ribbon", JLabel.CENTER), BorderLayout.CENTER)
    }
}

