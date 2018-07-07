import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException("No root element is found")
        render(root) {

            gameRoot()
        }
    }
}