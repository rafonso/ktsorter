package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.layout.Region
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType
import rafael.ktsorter.views.Limits

class HorizontalBarsPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    private lateinit var indexToYRegion: List<Double>
    private var height: Double = 0.0

    private fun valueToBar(index: Int, value: Int): Rectangle =
            Rectangle(0.0, indexToYRegion[index], region.width, height).also { r ->
                r.fill = colors[value - 1]
            }

    override fun initPlotter() {
        height = region.height / limits.quantity
        indexToYRegion = (0 until limits.quantity).map { it * height }
    }

    override fun plotValues(values: List<Int>): List<Shape> = values.mapIndexed(this::valueToBar)

    override fun plotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType) {
        basicPlotPositions(shapes, positions, eventType, limits)
    }

}
