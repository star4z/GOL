import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.UIManager

class MainFrame : JFrame("Conway's Game of Life") {
    init {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()
        val mainPanel = MainPanel()
        mainPanel.resizeCallbacks.add { pack() }
        add(mainPanel, BorderLayout.CENTER)
        Grid.updateListeners.add {
            mainPanel.onNext()
        }
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

}