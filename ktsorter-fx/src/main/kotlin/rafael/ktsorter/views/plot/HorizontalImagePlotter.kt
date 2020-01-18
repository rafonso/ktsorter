package rafael.ktsorter.views.plot

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.PixelReader
import javafx.scene.image.WritableImage
import javafx.scene.layout.Region
import rafael.ktsorter.views.Limits

class HorizontalImagePlotter(region: Region, initialValues: IntArray, limits: Limits) :
    ImagePlotter(region, initialValues, limits) {

    private lateinit var rowValues: List<Double>
    private var tileHeight = 0
    private var tileWidth = 0

    override fun prepareImage(originalImage: Image) {
        tileHeight = (originalImage.height.toInt() / limits.quantity)
        tileWidth = originalImage.width.toInt()
        rowValues = (0 until limits.quantity).map { it * tileHeight.toDouble() }
    }

    override fun pixelToTiles(reader: PixelReader): List<Image> =
        rowValues.map { WritableImage(reader, 0, it.toInt(), tileWidth, tileHeight) }

    override fun moveImage(tile: ImageView, index: Int) {
        tile.translateX = 0.0
        tile.translateY = rowValues[index]
    }

}
