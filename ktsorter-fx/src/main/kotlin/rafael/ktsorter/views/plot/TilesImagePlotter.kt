package rafael.ktsorter.views.plot

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.PixelReader
import javafx.scene.image.WritableImage
import javafx.scene.layout.Region

class TilesImagePlotter(region: Region, initialValues: IntArray, limits: Limits) :
    ImagePlotter(region, initialValues, limits) {

    private lateinit var tilesValues: List<Pair<Double, Double>>
    private var tileHeight = 0
    private var tileWidth = 0

    override fun prepareImage(originalImage: Image) {
        tileHeight = originalImage.height.toInt() / limits.rows
        tileWidth = originalImage.width.toInt() / limits.columns
        tilesValues = (0..limits.quantity).map {
            Pair(
                it % limits.columns * tileWidth.toDouble(),
                it / limits.columns * tileWidth.toDouble()
            )
        }
    }

    override fun pixelToTiles(reader: PixelReader): List<Image> =
        tilesValues.map { WritableImage(reader, it.first.toInt(), it.second.toInt(), tileWidth, tileHeight) }

    override fun moveImage(tile: ImageView, index: Int) {
        tile.translateX = tilesValues[index].first
        tile.translateY = tilesValues[index].second
    }

}
