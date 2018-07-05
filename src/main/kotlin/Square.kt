import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button

class Square : RComponent<SimpleProps<String?>, RState>() {

    override fun RBuilder.render() {
        button(classes = "square") {
            +(props.value ?: "")
            attrs.onClickFunction = props.onClickFunction
        }
    }
}

fun RBuilder.square(value: String?, onClickFunction: (Event) -> Unit) = child(Square::class) {
    attrs.value = value
    attrs.onClickFunction = onClickFunction
}