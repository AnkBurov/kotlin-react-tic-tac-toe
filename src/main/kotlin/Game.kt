import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div
import react.dom.li
import react.dom.ol

class Game : RComponent<RProps, GameState>() {

    override fun componentWillMount() {
        setState {
            history = mutableListOf(HistoryState(arrayOfNulls(9), Player.X))
        }
    }

    override fun RBuilder.render() {

        div("game") {
            div("game-board") {
                board(state.history.last().squares) {i -> {
                        handleClick(i)
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
        with(gameState) {
            val current = history.last()

            val winner = calculateWinner(current.squares)

            val status = when (winner) {
                Player.X, Player.O -> "Winner: $winner"
                else -> "Next player: ${current.nextPlayer}"
            }
            return status
        }
    }

    private fun calculateWinner(squares: Array<Player?>): Player? {
        for ((a, b, c) in lines) {
            if (squares[a] == squares[b] && squares[a] == squares[c]) {
                return squares[a]
            }
        }
        return null
    }

    private fun handleClick(i: Int) {
        val current = state.history.last()

        if (calculateWinner(current.squares) != null || current.squares[i] != null) {
            return
        }
        val updatedSquares = current.squares.copyOf()
        updatedSquares[i] = current.nextPlayer
        setState {
            val newState = HistoryState(updatedSquares, when (current.nextPlayer) {
                Player.X -> Player.O
                Player.O -> Player.X
            })
            history = ArrayList(history + newState)
        }
    }

    companion object {
        private val lines = listOf(
                Triple(0, 1, 2),
                Triple(3, 4, 5),
                Triple(6, 7, 8),
                Triple(0, 3, 6),
                Triple(1, 4, 7),
                Triple(2, 5, 8),
                Triple(0, 4, 8),
                Triple(2, 4, 6)
        )
    }
}

class GameState(var history: List<HistoryState>) : RState

class HistoryState(var squares: Array<Player?>,
                   var nextPlayer: Player)

fun RBuilder.game() = child(Game::class) {}