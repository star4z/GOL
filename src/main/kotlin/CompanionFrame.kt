import Grid.scale
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.JSlider



class CompanionFrame : JFrame() {
    @Volatile
    var continuing = false

    @Volatile
    var frameRate = 15

    init {
        size = Dimension(250, 1000)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        addButton("Step") {
            Grid.doNext()
        }



        addButton("Start") {
            GlobalScope.launch {
                continuing = true
                while (continuing) {
                    Grid.doNext()
                    delay((1000 / frameRate).toLong())
                }
            }
        }

        addButton("Stop") {
            continuing = false
        }

        addSlider("FPS", 0, 144, 15, 15) {
            val source = it.source as JSlider
            if (!source.valueIsAdjusting) {
                frameRate = source.value
            }
        }

        addSlider("Scale", 1, 25, 25, 5) {
            val source = it.source as JSlider
            if (!source.valueIsAdjusting) {
                scale = source.value
            }
        }

        isVisible = true
    }

    private fun addSlider(text: String, min: Int = 0, max: Int, init: Int = 0, tickSpacing: Int = 10, callback: (ChangeEvent) -> Unit) {
        val label = JLabel(text, JLabel.CENTER)
        label.alignmentX = Component.CENTER_ALIGNMENT
        add(label)

        val slider = JSlider(JSlider.HORIZONTAL, min, max, init)
        slider.addChangeListener(callback)
        slider.majorTickSpacing = tickSpacing
        slider.paintTicks = true
        slider.paintLabels = true
        add(slider)
    }

    private fun addButton(text: String, callback: () -> Unit) {
        val stepButton = JButton(text)
        stepButton.addActionListener { callback() }
        stepButton.alignmentX = Component.CENTER_ALIGNMENT
        add(stepButton)
    }
}