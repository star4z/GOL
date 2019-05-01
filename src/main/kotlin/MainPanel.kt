import Grid.scale
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel
import kotlin.collections.ArrayList


class MainPanel : JPanel() {

    var resizeCallbacks = ArrayList<() -> Unit>()

    init {
        preferredSize = Dimension(Grid.w * scale, Grid.h * scale)
        Grid.drawLightweightSpaceship(5, 5)

        Grid.scaleChangeListeners.add {
            preferredSize = Dimension(Grid.w * scale, Grid.h * scale)
            repaint()
            resizeCallbacks.forEach { it() }
        }

        isVisible = true
    }

    fun onNext() {
        repaint()
    }


    override fun paint(g: Graphics) {
        for (i in Grid.currentState.indices) {
            for (j in Grid.currentState[i].indices) {
                if (Grid.currentState[i][j]) {
                    g.color = Color.BLACK
                } else {
                    g.color = Color.WHITE
                }
                g.fillRect(i * scale, j * scale, scale, scale)

            }
        }
    }


}

fun print(a: Array<Array<Boolean>>) {

    for (y in a.indices) {
        print("$y\t|")
        for (x in a[y].indices) {
            print("${if (a[x][y]) "*" else "."} ")
        }
        println()
    }
    println()
}