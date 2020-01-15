package rafael.ktsorter.views.plot

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.PixelReader
import javafx.scene.image.WritableImage
import javafx.scene.layout.Region

class VerticalImagePlotter(region: Region, initialValues: IntArray, limits: Limits) :
    ImagePlotter(region, initialValues, limits) {

    private lateinit var colValues: List<Double>
    private var tileHeight = 0
    private var tileWidth = 0

    override fun prepareImage(originalImage: Image) {
        tileHeight = originalImage.height.toInt()
        tileWidth = originalImage.width.toInt() / limits.quantity
        colValues = (0..limits.quantity).map { it * tileWidth.toDouble() }
    }

    override fun pixelToTiles(reader: PixelReader): List<Image> =
        colValues.map { WritableImage(reader, it.toInt(), 0, tileWidth, tileHeight) }

    override fun moveImage(tile: ImageView, index: Int) {
        tile.translateX = colValues[index]
        tile.translateY = 0.0
    }

}
