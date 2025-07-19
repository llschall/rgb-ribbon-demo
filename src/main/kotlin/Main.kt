import org.llschall.rgbstrip.model.AppModel
import org.llschall.rgbstrip.controller.AppController
import org.llschall.rgbstrip.view.AppView

fun main() {
    val model = AppModel()
    val controller = AppController(model)
    controller.loadVersion()
    val view = AppView(model, controller)
    view.show()
}
