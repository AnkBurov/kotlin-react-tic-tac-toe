import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.ol

class Game : RComponent<RProps, RState>() {
    override fun RBuilder.render() {

        div("game") {
            div("game-board") {
                board()
            }

            div("game-info") {
                div {  }
                ol {  }
            }
        }
    }
}

fun RBuilder.game() = child(Game::class) {}