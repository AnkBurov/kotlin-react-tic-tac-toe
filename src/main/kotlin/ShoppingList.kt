import react.RBuilder
import react.dom.div
import react.dom.h1
import react.dom.li
import react.dom.ul

fun RBuilder.shoppingList(name: String) {
    div("shopping-list") {
        h1 { +"Shopping list for $name" }
        ul {
            li { +"Instagram" }
            li { +"WhatsApp" }
            li { +"Oculus" }
        }
    }
}