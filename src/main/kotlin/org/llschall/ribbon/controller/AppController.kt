package org.llschall.ribbon.controller

import org.llschall.rgbribbon.RgbRibbon
import org.llschall.ribbon.model.AppModel

class AppController(private val model: AppModel) {
    fun loadVersion() {
        model.version = RgbRibbon.VERSION
    }

    fun start() {
        model.ribbon.start()
    }

    fun toggleBuiltInLed() {
        model.ribbon.toggleBuiltInLed()
    }

}
