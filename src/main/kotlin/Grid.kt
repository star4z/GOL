object Grid {
    var scale = 25
        set(value) {
            updateListeners.forEach { it() }
            field = value
        }

    var h = 15
    var w = 15

    private var previousState = Array(w) { Array(h) { false } }
    var currentState = Array(w) { Array(h) { false } }

    var updateListeners = ArrayList<() -> Unit>()
    var scaleChangeListeners = ArrayList<() -> Unit>()


    fun doNext() {
        previousState = currentState
        currentState = Array(w) { Array(h) { false } }
        for (i in previousState.indices) {
            for (j in previousState[i].indices) {
                update(i, j)
            }
        }

        updateListeners.forEach { it() }
    }

    private fun update(x: Int, y: Int) {
        val n = numberOfNeighbors(x, y)
        currentState[x][y] = if (previousState[x][y]) {
            when {
                n < 2 -> false
                n == 2 || n == 3 -> true
                else -> false
            }
        } else {
            n == 3
        }
    }

    private fun numberOfNeighbors(x: Int, y: Int): Int {
        val xBounds = arrayOf(x > 0, true, x < previousState.size - 1)
        val yBounds = arrayOf(y > 0, true, y < previousState[x].size - 1)

        val x0 = x - 1
        val y0 = y - 1

        var numberOfNeighbors = 0

        for (i in 0..2) {
            for (j in 0..2) {
                if (xBounds[i] && yBounds[j] && !(x0 + i == x && y0 + j == y) && previousState[x0 + i][y0 + j]) {
                    numberOfNeighbors++
                }
            }
        }

        return numberOfNeighbors
    }

    fun drawLightweightSpaceship(x: Int, y: Int) {
        currentState[x + 1][y] = true
        currentState[x + 2][y] = true
        currentState[x + 3][y] = true
        currentState[x + 4][y] = true

        currentState[x][y + 1] = true
        currentState[x + 4][y + 1] = true

        currentState[x + 4][y + 2] = true

        currentState[x][y + 3] = true
        currentState[x + 3][y + 3] = true
    }
}