import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLSelectElement
import react.*
import react.dom.*
import kotlin.browser.document

class GameRoot : RComponent<RProps, GameRootState>() {

    override fun componentWillMount() {
        setState {
            fieldSize = 3
            victoryLength = 3
        }
    }

    override fun RBuilder.render() {
        div {
            renderSelectFieldSize()

            renderSelectVictoryLength()

            renderReinitButton()

            br {  }
            br {  }
            br {  }

            game(state.fieldSize, state.victoryLength)
        }
    }

    private fun RBuilder.renderSelectFieldSize() {
        select {
            attrs.id = "selectSize"
            option {
                attrs.disabled = true
                attrs.selected = true
                +FIELD_SIZE_DESCRIPTION
            }
            for (i in 3..18 step 3) {
                option {
                    +i.toString()
                    attrs.value = i.toString()
                }
            }
        }
    }

    private fun RBuilder.renderSelectVictoryLength() {
        select {
            attrs.id = "selectLength"
            option {
                attrs.disabled = true
                attrs.selected = true
                +VICTORY_LENGTH_DESCRIPTION
            }
            for (i in 3..18 step 3) {
                option {
                    +i.toString()
                    attrs.value = i.toString()
                }
            }
        }
    }

    private fun RDOMBuilder<DIV>.renderReinitButton() {
        button {
            +"Reinit"
            attrs.onClickFunction = {
                val selectSize = document.getElementById("selectSize") as HTMLSelectElement
                val selectLength = document.getElementById("selectLength") as HTMLSelectElement
                setState {
                    fieldSize = if (selectSize.value == FIELD_SIZE_DESCRIPTION) fieldSize else selectSize.value.toInt()
                    victoryLength = if (selectLength.value == VICTORY_LENGTH_DESCRIPTION) victoryLength else selectLength.value.toInt()
                }
            }
        }
    }

    companion object {
        private val FIELD_SIZE_DESCRIPTION = "Field size"
        private val VICTORY_LENGTH_DESCRIPTION = "Needed victory length"
    }
}

class GameRootState(var fieldSize: Int, var victoryLength: Int) : RState

fun RBuilder.gameRoot() = child(GameRoot::class) {}