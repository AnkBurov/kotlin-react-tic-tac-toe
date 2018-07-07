import kotlinx.html.js.onClickFunction
import model.BoardModel
import model.Player
import model.Player.*
import model.isPlayer
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div
import react.dom.li
import react.dom.ol

class Game : RComponent<GameProps, GameState>() {

    override fun componentWillMount() {
        setState {
            val board = BoardModel(props.fieldSize, props.victoryLength)
            history = mutableListOf(HistoryState(board))
        }
    }

    override fun componentWillReceiveProps(nextProps: GameProps) {
        val board = BoardModel(nextProps.fieldSize, nextProps.victoryLength)
        state.history = mutableListOf(HistoryState(board))
    }

    override fun RBuilder.render() {

        div("game") {
            div("game-board") {
                board(state.history.last().board) { row, column ->
                    {
                        handleClick(row, column)
                    }
                }
            }

            div("game-info") {
                div { +getStatus(state) }
                ol { renderMoves() }
            }
        }
    }

    private fun RBuilder.renderMoves() {
        state.history.mapIndexed { index, _ ->
            when (index) {
                0 -> "Go to game start" to index
                else -> "Go to move #$index" to index
            }
        }.forEach { (message, index) ->
            li {
                button {
                    +message
                    attrs.onClickFunction = jumpToHistoryState(index)
                }
            }
        }
    }

    private fun jumpToHistoryState(index: Int): (Event) -> Unit {
        return {
            setState {
                history = history.subList(0, index + 1)
            }
        }
    }

    private fun getStatus(gameState: GameState): String {
        val current = gameState.history.last()

        val winner = current.board.calculateWinner()

        return when {
            winner.isPlayer() -> "Winner: $winner"
            current.board.isFull() -> "Draw"
            else -> "Next player: ${current.nextPlayer}"
        }
    }

    private fun handleClick(row: Int, column: Int) {
        val current = state.history.last()
        val currentPlayer = current.nextPlayer

        if (current.board.calculateWinner() != null || current.board.getCell(row, column) != null) {
            return
        }

        val newBoard = current.board.copyOf()
        newBoard.setCell(row, column, currentPlayer)

        setState {
            val newState = HistoryState(newBoard, currentPlayer.other())
            history += newState
        }
    }
}

class GameProps(var fieldSize: Int, var victoryLength: Int) : RProps

class GameState(var history: List<HistoryState>) : RState

class HistoryState(var board: BoardModel,
                   var nextPlayer: Player = X)

fun RBuilder.game(fieldSize: Int, victoryLength: Int) = child(Game::class) {
    attrs.fieldSize = fieldSize
    attrs.victoryLength = victoryLength
}