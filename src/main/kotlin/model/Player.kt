package model

enum class Player {
    X,
    O;

    fun other(): Player {
        return when (this) {
            X -> O
            O -> X
        }
    }
}

fun Player?.isPlayer() = this != null