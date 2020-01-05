package rafael.ktsorter.views.plot

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType
import kotlin.math.*

class RadialScatterPlotter(region: Region, initialValues: IntArray, limits: Limits) :
    Plotter(region, initialValues, limits) {

    private lateinit var centerRegion: CartesianCoordinate
    private lateinit var ellipseAxes: CartesianCoordinate
    private var deltaTheta: Double = 0.0

    private fun valueToRadial(index: Int, value: Int): Pair<PolarCoordinate, Color> {
        val theta = (index + 1) * deltaTheta
        val ellipseRadius = (ellipseAxes.x * ellipseAxes.y) / sqrt(
            (ellipseAxes.x * sin(theta)).pow(2) + (ellipseAxes.y * cos(theta)).pow(2)
        )
        val valueRadius = (value * ellipseRadius) / limits.quantity

        return Pair(PolarCoordinate(valueRadius, theta), colors[value - 1])
    }

    private fun toCircle(coordinate: CartesianCoordinate, color: Color) =
        Circle(coordinate.x, coordinate.y, limits.radius, color).also { c ->
            c.stroke = Color.BLACK
            c.strokeWidth = limits.radius / 4
        }

    override fun initPlotter() {
        centerRegion = CartesianCoordinate(super.region.width / 2, super.region.height / 2)
        ellipseAxes = CartesianCoordinate(centerRegion.x * ellipseFactor, centerRegion.y * ellipseFactor)
        deltaTheta = (2 * PI) / super.limits.quantity
    }

    override fun plotValues(values: List<Int>): List<Shape> =
            values.asSequence().mapIndexed(this::valueToRadial)
                    .map { (polarCoord, color) -> Pair(polarCoord.cartesianCoordinate, color) }
                    .map { (coord, color) -> Pair(CartesianCoordinate(coord.x, - coord.y), color) }
                    .map { (coord, color) -> Pair(coord + centerRegion, color) }
                    .map { (coord, color) -> this.toCircle(coord, color) }.toList()

    override fun plotPositions(shapes: List<Shape>, positions: List<Int>, eventType: EventType) {
        basicPlotPositions(shapes, positions, eventType, limits)
    }

}
