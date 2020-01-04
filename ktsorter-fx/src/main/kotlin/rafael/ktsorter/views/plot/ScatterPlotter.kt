package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType

class ScatterPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    private lateinit var indexToRegion: List<Double>

    private fun valueToCircle(index: Int, value: Int): Circle {
        val xRegion = indexToRegion[index]
        val yRegion = yToRegion(value - 1)
        val color = colors[value - 1]

        return Circle(xRegion, yRegion, limits.radius, color).also { c ->
            c.stroke = Color.BLACK
            c.strokeWidth = limits.radius / 4
        }
    }

    override fun initPlotter() {
        indexToRegion = 0.rangeTo(super.limits.quantity).map { xToRegion(it + 1) }
    }

    override fun plotValues(values: List<Int>): List<Shape> = values.mapIndexed(this::valueToCircle)

    override fun plotPositions(shapes: List<Shape>, positions: List<Int>, eventType: EventType) {
        basicPlotPositions(shapes, positions, eventType, limits)
    }

}

