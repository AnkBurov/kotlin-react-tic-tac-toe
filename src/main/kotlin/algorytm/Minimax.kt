package algorytm

import model.BoardModel
import model.Cell
import model.Player
import kotlin.js.Math
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

object Minimax {

    fun getBestMove(board: BoardModel, forPlayer: Player): Cell? {
        val scoredMoves = board.getEmptyCells()
                .map { emptyCell ->
                    val newBoard = board.copyOf()
                    newBoard.setCell(emptyCell.first, emptyCell.second, forPlayer)

                    val score = analyzeMove(newBoard, forPlayer, forPlayer.other())

                    emptyCell to score
                }
                .sortedBy { it.second }
        val bestScoredMove = scoredMoves.lastOrNull() ?: return null
        //filter out best moves and get random of it
        val bestScoredMoves = scoredMoves.filter { it.second == bestScoredMove.second }

        val indexOfBestRandomMove = getRandomInt(0, bestScoredMoves.size)
        return bestScoredMoves[indexOfBestRandomMove].first
    }

    fun analyzeMove(board: BoardModel, forPlayer: Player, nextPlayer: Player, depth: Int = 0): Int {
        val isTerminalScore = calculateScore(board, forPlayer, depth)
        if (isTerminalScore != null) {
            return isTerminalScore
        }

        var bestVal = if (forPlayer == nextPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        for ((rowIndex, columnIndex) in board.getEmptyCells()) {
            board.setCell(rowIndex, columnIndex, nextPlayer)

            val moveScore = analyzeMove(board, forPlayer, nextPlayer.other(), depth + 1)
            bestVal = when (forPlayer) {
                nextPlayer -> max(bestVal, moveScore)
                else -> min(bestVal, moveScore)
            }

            board.emptyCell(rowIndex, columnIndex)
        }
        return bestVal
    }

    fun calculateScore(board: BoardModel, player: Player, depth: Int): Int? {
        val winner = board.calculateWinner()

        return when {
            winner == player -> 10 - depth
            winner == player.other() -> depth - 10
            board.isFull() -> 0
            else -> null
        }
    }

    fun getRandomInt(min: Int, max: Int): Int {
        return (floor(Math.random() * (max - min)) + min).toInt()
    }
}