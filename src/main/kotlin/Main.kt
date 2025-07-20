import org.llschall.ribbon.model.AppModel
import org.llschall.ribbon.controller.AppController
import org.llschall.ribbon.view.AppView

fun main() {
    val model = AppModel()
    val controller = AppController(model)
    controller.loadVersion()
    val view = AppView(model, controller)
    view.show()
}
