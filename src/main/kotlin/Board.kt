import react.*
import react.dom.div

class Board : RComponent<RProps, BoardState>() {

    override fun componentWillMount() {
        setState {
            squares = arrayOfNulls(9)
            nextPlayer = Player.X
        }
    }

    override fun RBuilder.render() {

        div {
            div("status") { +getStatus(state) }

            for (i in 0..8 step 3) {
                div("board-row") {
                    for (j in i..i + 2) {
                        renderSquare(j)
                    }
                }
            }
        }
    }

    private fun RBuilder.renderSquare(i: Int) {
        square(state.squares[i]) {
            handleClick(i)
        }
    }

    private fun getStatus(boardState: BoardState): String {
        with(boardState) {
            val winner = calculateWinner(squares)

            val status = when (winner) {
                Player.X, Player.O -> "Winner: $winner"
                else -> "Next player: $nextPlayer"
            }
            return status
        }
    }

    private fun handleClick(i: Int) {
        if (calculateWinner(state.squares) != null) {
            return
        }
        val updatedSquares = state.squares.copyOf()
        updatedSquares[i] = state.nextPlayer
        setState {
            squares = updatedSquares
            nextPlayer = when (nextPlayer) {
                Player.X -> Player.O
                Player.O -> Player.X
            }
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

class BoardState(var squares: Array<Player?>,
                 var nextPlayer: Player) : RState

fun RBuilder.board() = child(Board::class) {}