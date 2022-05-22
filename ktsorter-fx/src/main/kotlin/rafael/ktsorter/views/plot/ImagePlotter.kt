package rafael.ktsorter.views.plot

import javafx.scene.Node
import javafx.scene.effect.ColorAdjust
import javafx.scene.effect.SepiaTone
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.PixelReader
import javafx.scene.layout.Region
import rafael.ktsorter.sorter.events.EventType
import rafael.ktsorter.views.Limits

abstract class ImagePlotter(region: Region, initialValues: IntArray, limits: Limits) :
        Plotter(region, initialValues, limits) {

    private lateinit var tiles: Array<ImageView>

    protected abstract fun prepareImage(originalImage: Image)

    protected abstract fun pixelToTiles(reader: PixelReader): List<Image>

    protected abstract fun moveImage(tile: ImageView, index: Int)

    private val comparsionEffect = SepiaTone(0.1)

    private val swapEffect = ColorAdjust(-0.05, .8, 0.9, 0.4)

    override fun initPlotter() {
        val originalImage = Image("mona1.jpg")
        prepareImage(originalImage)

        val reader = originalImage.pixelReader!!
        tiles = pixelToTiles(reader).map { ImageView(it) }.toTypedArray()
    }

    override fun plotValues(values: List<Int>): List<Node> = values.mapIndexed { index, value ->
        tiles[value - 1].also { tile ->
            moveImage(tile, index)
        }
    }

    override fun plotPositions(shapes: List<Node>, positions: List<Int>, eventType: EventType) {
        val effect = when (eventType) {
            EventType.COMPARSION          -> comparsionEffect
            EventType.SWAP, EventType.SET -> swapEffect
            else                          -> null
        }

        shapes
            .mapIndexed { index, shape -> Pair(index, shape as ImageView) }
            .forEach {
                it.second.effect = if (it.first in positions) effect else null
            }
    }
}



