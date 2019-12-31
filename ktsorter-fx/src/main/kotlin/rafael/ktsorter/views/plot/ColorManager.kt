package rafael.ktsorter.views.plot

import javafx.scene.paint.Color

object ColorManager {

    private fun valueToR(x: Double): Double =
        when {
            x < 0.2 -> 0.29 - 1.45 * x
            x < 0.4 -> 0.0
            x < 0.6 -> - 2.00 + 5.00 * x
            x < 0.8 -> 1.39 - 0.65 * x
            else -> 0.35 + 0.65 * x
        }

    private fun valueToG(x: Double): Double =
        when {
            x < 0.2 -> 0.00
            x < 0.4 -> - 1.00 + 5.00 * x
            x <= 0.6 -> 1.00
            else -> 2.50 - 2.50 * x
        }

    private fun valueToB(x: Double): Double =
        when {
            x < 0.2 -> 0.50 + 2.50 * x
            x < 0.4 -> 2.00 - 5.00 * x
            else -> 0.0
        }

    private fun toInt(x: Double) = (x * 255).toInt()

    fun getColors(max: Int): List<Color> = (1 until max + 1)
        .map { it.toDouble() / max }
        .map { Triple(valueToR(it), valueToG(it), valueToB(it)) }
        .map { (r, g, b) -> Triple(toInt(r), toInt(g), toInt(b)) }
        .map { (r, g, b) -> Color.rgb(r, g, b) }

}
