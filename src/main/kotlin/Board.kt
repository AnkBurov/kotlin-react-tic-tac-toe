import org.w3c.dom.events.Event
import react.*
import react.dom.div

fun RBuilder.board(squares: Array<Player?>, onClickFunction: (Int) -> (Event) -> Unit) {

    div {
        for (i in 0..8 step 3) {
            div("board-row") {
                for (j in i..i + 2) {
                    renderSquare(j, squares, onClickFunction)
                }
            }
        }
    }
}

private fun RBuilder.renderSquare(index: Int, squares: Array<Player?>, onClickFunction: (Int) -> (Event) -> Unit) {
    square(squares[index], onClickFunction(index))
}