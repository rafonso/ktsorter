package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import rafael.ktsorter.sorter.events.EventType
import rafael.ktsorter.views.Limits

class TilesPlotter(region: Region, initialValues: IntArray, limits: Limits) : Plotter(region, initialValues, limits) {

    private var tilesHeight = 0.0
    private var tilesWidth = 0.0
    private lateinit var tilesCoordinates: List<CartesianCoordinate>

    private val comparsionEffect = { shape: Shape -> shape.fill = ((shape.fill) as Color).grayscale() }
    private val swapEffect       = { shape: Shape -> shape.fill = ((shape.fill) as Color).invert()    }
    private val noEffect         = { _    : Shape -> }

    private fun valueToTile(index: Int, value: Int): Rectangle =
        Rectangle(tilesCoordinates[index].x, tilesCoordinates[index].y, tilesWidth, tilesHeight).apply {
            fill = colors[value - 1]
        }

    override fun initPlotter() {
        tilesHeight = region.height / limits.rows
        tilesWidth = region.width / limits.columns
        tilesCoordinates = (0 until limits.columns).flatMap { col ->
            (0 until limits.rows).map { row ->
                CartesianCoordinate(col * tilesWidth, row * tilesHeight)
            }
        }
    }

    override fun plotValues(values: List<Int>): List<Shape> = values.mapIndexed(this::valueToTile)

    override fun plotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType) {
        val effect = when (eventType) {
            EventType.COMPARSION          -> comparsionEffect
            EventType.SWAP, EventType.SET -> swapEffect
            else                          -> noEffect
        }

        positions.map { shapes[it] }.map { it as Shape }.forEach(effect)
    }
}
