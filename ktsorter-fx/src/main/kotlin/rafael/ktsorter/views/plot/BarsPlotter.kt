package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.add

class BarsPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    override fun plotValues(values: IntArray) {
        val indexToRegion = 0.rangeTo(super.limits.quantity).map { xToRegion(it + 1) }
        val width = limits.radius
        val y0 = yToRegion(0)

        values.mapIndexed { index, value ->
            val xRegion = indexToRegion[index]
            val yRegion = yToRegion(value)
            val color = colors[value - 1]
            val x = xRegion - width / 2

            Rectangle(x, yRegion, width, y0 - yRegion).also { r ->
                r.fill = color
                r.stroke = Color.BLACK
                r.strokeWidth = limits.radius / 4
            }
        }.forEach {
            region.add(it)
        }
    }
}
