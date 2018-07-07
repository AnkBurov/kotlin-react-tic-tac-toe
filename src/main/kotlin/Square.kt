import kotlinx.html.js.onClickFunction
import model.Player
import org.w3c.dom.events.Event
import react.*
import react.dom.button

fun RBuilder.square(player: Player?, onClickFunction: (Event) -> Unit) {
    button(classes = "square") {
        +(player?.name ?: "")
        attrs.onClickFunction = onClickFunction
    }
}