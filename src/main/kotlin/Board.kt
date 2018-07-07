import model.BoardModel
import org.w3c.dom.events.Event
import react.*
import react.dom.div

fun RBuilder.board(board: BoardModel, onClickFunction: (Int, Int) -> (Event) -> Unit) {
    div {
        for (row in 0 until board.size) {
            div("board-row") {
                for (column in 0 until board.size) {
                    renderSquare(row, column, board, onClickFunction)
                }
            }
        }
    }
}

private fun RBuilder.renderSquare(row: Int, column: Int, board: BoardModel,
                                  onClickFunction: (Int, Int) -> (Event) -> Unit) {
    square(board.getCell(row, column), onClickFunction(row, column))
}