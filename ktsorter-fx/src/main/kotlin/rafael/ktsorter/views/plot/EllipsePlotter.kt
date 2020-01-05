package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType

class EllipsePlotter(region: Region, initialValues: IntArray, limits: Limits) :
        Plotter(region, initialValues, limits) {

    private lateinit var centerRegion: CartesianCoordinate
    private lateinit var ellipseAxes: CartesianCoordinate

    private fun valueToEllipse(index: Int, value: Int): Shape {
        val radiusX = ellipseAxes.x * index / limits.quantity
        val radiusY = ellipseAxes.y * index / limits.quantity
        val color = colors[value - 1]

        return Ellipse(centerRegion.x, centerRegion.y, radiusX, radiusY).also { el ->
            el.fill = Color.TRANSPARENT
            el.stroke = color
            el.strokeWidth = limits.radius
        }
    }

    override fun initPlotter() {
        centerRegion = CartesianCoordinate(super.region.width / 2, super.region.height / 2)
        ellipseAxes = CartesianCoordinate(centerRegion.x * ellipseFactor, centerRegion.y * ellipseFactor)
    }

    override fun plotValues(values: List<Int>): List<Shape> = values.mapIndexed(this::valueToEllipse)

    override fun plotPositions(shapes: List<Shape>, positions: List<Int>, eventType: EventType) {
        basicPlotPositions(shapes, positions, eventType, limits)
    }
}
