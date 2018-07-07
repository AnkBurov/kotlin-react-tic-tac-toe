package model

import model.Player.O
import model.Player.X

data class BoardModel(private val size: Int,
                      private val victoryLength: Int = 3,
                      private val rows: List<Row> = arrayOfNulls<Row>(size)
                              .map { arrayOfNulls<Player?>(size).toMutableList() }
                              .toList()) {

    fun setCell(row: Int, column: Int, player: Player): Boolean {
        return if (rows[row][column] != null) {
            false
        } else {
            rows[row][column] = player
            true
        }
    }

    fun getCell(row: Int, column: Int) = rows[row][column]

    fun calculateWinner(): Player? {
        for (rowIndex in 0 until rows.size) {
            for (columnIndex in 0 until rows[rowIndex].size) {
                return sequenceOf(checkRight(rowIndex, columnIndex),
                        checkBottom(rowIndex, columnIndex),
                        checkUpperRight(rowIndex, columnIndex),
                        checkBottomRight(rowIndex, columnIndex))
                        .filterNotNull()
                        .firstOrNull() ?: continue
            }
        }

        return null
    }

    fun copyOf(): BoardModel {
        val newRows = mutableListOf<Row>()
        for (row in rows) {
            val newRow = mutableListOf<Player?>()
            for (column in row) {
                newRow += column
            }
            newRows += newRow
        }
        return BoardModel(size, victoryLength, newRows)
    }

    private fun checkRight(rowIndex: Int,
                           columnIndex: Int,
                           xCounter: Int = 0,
                           oCounter: Int = 0): Player? {
        return checkLineForWinner(rowIndex, columnIndex, xCounter, oCounter) { cell ->
            when (cell) {
                X -> checkRight(rowIndex, columnIndex + 1, xCounter + 1, oCounter)
                O -> checkRight(rowIndex, columnIndex + 1, xCounter, oCounter + 1)
                else -> null
            }
        }
    }

    private fun checkBottom(rowIndex: Int,
                            columnIndex: Int,
                            xCounter: Int = 0,
                            oCounter: Int = 0): Player? {
        return checkLineForWinner(rowIndex, columnIndex, xCounter, oCounter) { cell ->
            when (cell) {
                X -> checkBottom(rowIndex + 1, columnIndex, xCounter + 1, oCounter)
                O -> checkBottom(rowIndex + 1, columnIndex, xCounter, oCounter + 1)
                else -> null
            }
        }
    }

    private fun checkUpperRight(rowIndex: Int,
                                columnIndex: Int,
                                xCounter: Int = 0,
                                oCounter: Int = 0): Player? {
        return checkLineForWinner(rowIndex, columnIndex, xCounter, oCounter) { cell ->
            when (cell) {
                X -> checkUpperRight(rowIndex - 1, columnIndex + 1, xCounter + 1, oCounter)
                O -> checkUpperRight(rowIndex - 1, columnIndex + 1, xCounter, oCounter + 1)
                else -> null
            }
        }
    }

    private fun checkBottomRight(rowIndex: Int,
                                 columnIndex: Int,
                                 xCounter: Int = 0,
                                 oCounter: Int = 0): Player? {
        return checkLineForWinner(rowIndex, columnIndex, xCounter, oCounter) { cell ->
            when (cell) {
                X -> checkBottomRight(rowIndex + 1, columnIndex + 1, xCounter + 1, oCounter)
                O -> checkBottomRight(rowIndex + 1, columnIndex + 1, xCounter, oCounter + 1)
                else -> null
            }
        }
    }

    private fun checkLineForWinner(rowIndex: Int,
                                   columnIndex: Int,
                                   xCounter: Int = 0,
                                   oCounter: Int = 0,
                                   whichLineChecker: (Player?) -> Player?) : Player? {
        if (xCounter >= victoryLength) {
            return X
        } else if (oCounter >= victoryLength) {
            return O
        }

        if (rowIndex < 0 || columnIndex < 0 || rowIndex > rows.size - 1 || columnIndex > rows[rowIndex].size - 1) {
            return null//out of bounds
        }

        val cell = rows[rowIndex][columnIndex]
        return whichLineChecker(cell)
    }

    /*private fun checkMiddleRightForWinner(rowIndex: Int,
                                          columnIndex: Int,
                                          xCounter: Int = 0,
                                          oCounter: Int = 0) : Player? {
        if (xCounter > victoryLength) {
            return X
        } else if (oCounter > victoryLength) {
            return O
        }

        if (rowIndex > rows.size - 1 || columnIndex > rows[rowIndex].size - 1) {
            return null//out of bounds
        }

        val cell = rows[rowIndex][columnIndex]
        return when (cell) {
            X -> checkMiddleRightForWinner(rowIndex + 1, columnIndex, xCounter + 1, oCounter)
            O -> checkMiddleRightForWinner(rowIndex + 1, columnIndex, xCounter, oCounter + 1)
            else -> null
        }
    }*/
}

typealias Row = MutableList<Player?>