package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType

class BarsPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    private lateinit var indexToRegion: List<Double>
    private var width: Double = 0.0
    private var y0: Double = 0.0

    private fun valueToBar(index: Int, value: Int): Rectangle {
        val xRegion = indexToRegion[index]
        val yRegion = yToRegion(value)
        val color = colors[value - 1]
        val x = xRegion - width / 2

        return Rectangle(x, yRegion, width, y0 - yRegion).also { r ->
            r.fill = color
            r.stroke = Color.BLACK
            r.strokeWidth = limits.radius / 4
        }
    }

    override fun initPlotter() {
        indexToRegion = 0.rangeTo(super.limits.quantity).map { xToRegion(it + 1) }
        width = limits.radius
        y0 = yToRegion(0)
    }

    override fun plotValues(values: List<Int>): List<Shape> = values.mapIndexed(this::valueToBar)

    override fun plotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType) {
        basicPlotPositions(shapes, positions, eventType, limits)
    }
}
