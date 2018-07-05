import react.*
import react.dom.div

class Board : RComponent<RProps, BoardState>() {

    override fun componentWillMount() {
        setState {
            squares = arrayOfNulls(9)
        }
    }

    override fun RBuilder.render() {
        val status = "Next player: X"

        div {
            div("status") { +status }

            div("board-row") {
                for (i in 0..2) {
                    renderSquare(i)
                }
            }

            div("board-row") {
                for (i in 3..5) {
                    renderSquare(i)
                }
            }

            div("board-row") {
                for (i in 6..8) {
                    renderSquare(i)
                }
            }
        }
    }

    private fun RBuilder.renderSquare(i: Int) {
        square(state.squares[i]) {
            handleClick(i)
        }
    }

    private fun handleClick(i: Int) {
        val updatedSquares = state.squares.copyOf()
        updatedSquares[i] = "X"
        setState {
            squares = updatedSquares
        }
    }

}

class BoardState(var squares: Array<String?>) : RState

fun RBuilder.board() = child(Board::class) {}