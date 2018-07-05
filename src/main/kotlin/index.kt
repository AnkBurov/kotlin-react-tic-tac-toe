import kotlinx.html.js.onClickFunction
import react.dom.button
import react.dom.h1
import react.dom.h2
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException("No root element is found")
        render(root) {

            game()
            /*h1 { +"Hello world!" }
            h2 { +"Second line" }

            shoppingList("SomeName")*/
        }
    }
}