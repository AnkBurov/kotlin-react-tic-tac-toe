import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import model.GameMode
import org.w3c.dom.HTMLSelectElement
import react.*
import react.dom.*
import kotlin.browser.document

class GameRoot : RComponent<RProps, GameRootState>() {

    override fun componentWillMount() {
        setState {
            fieldSize = 3
            victoryLength = 3
            gameMode = GameMode.HUMAN_VS_HUMAN
        }
    }

    override fun RBuilder.render() {
        div {
            renderSelectFieldSize()

            renderSelectVictoryLength()

            renderSelectGameMode()

            renderReinitButton()

            br {  }
            br {  }
            br {  }

            game(state.fieldSize, state.victoryLength, state.gameMode)
        }
    }

    private fun RBuilder.renderSelectFieldSize() {
        select {
            attrs.id = "selectSize"
            option {
                attrs.disabled = true
                attrs.selected = true
                +"Field size"
                attrs.value = 3.toString()
            }
            for (i in 3..18) {
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
                +"Needed victory length"
                attrs.value = 3.toString()
            }
            for (i in 3..18 step 3) {
                option {
                    +i.toString()
                    attrs.value = i.toString()
                }
            }
        }
    }

    private fun RBuilder.renderSelectGameMode() {
        select {
            attrs.id = "selectGameMode"
            option {
                attrs.selected = true
                +"Game mode: human versus human"
                attrs.value = GameMode.HUMAN_VS_HUMAN.name
            }

            option {
                +"Game mode: human versus ai"
                attrs.value = GameMode.HUMAN_VS_AI.name
            }
        }
    }

    private fun RDOMBuilder<DIV>.renderReinitButton() {
        button {
            +"Reinit"
            attrs.onClickFunction = {
                val selectSize = document.getElementById("selectSize") as HTMLSelectElement
                val selectLength = document.getElementById("selectLength") as HTMLSelectElement
                val selectGameMode = document.getElementById("selectGameMode") as HTMLSelectElement
                setState {
                    fieldSize = selectSize.value.toInt()
                    victoryLength = selectLength.value.toInt()
                    gameMode = GameMode.valueOf(selectGameMode.value)
                }
            }
        }
    }
}

class GameRootState(var fieldSize: Int, var victoryLength: Int, var gameMode: GameMode) : RState

fun RBuilder.gameRoot() = child(GameRoot::class) {}