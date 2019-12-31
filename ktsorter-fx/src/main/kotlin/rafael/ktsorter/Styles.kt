package rafael.ktsorter

import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val mainScreen by cssclass()
        val pad  by cssclass()
        val content by cssclass()
        val heading by cssclass()
        val controlsFields by cssclass()
        val labels  by cssclass()
        val buttons  by cssclass()
        val texts by cssclass()
        val canvases by cssclass()
    }

    init {
        mainScreen {
            prefHeight = 600.px
            prefWidth = 1280.px
            padding = box(10.px)
            pad {
                box(10.px)
            }
            controlsFields {
                padding = box(10.px)
                startMargin = 10.px
                endMargin = 10.px
            }
            labels {
                fontWeight = FontWeight.BOLD
                alignment = Pos.CENTER_RIGHT
            }
            buttons {
                prefWidth = 200.px
                endMargin = 10.px
                hAlignment = HPos.CENTER
            }
            texts {
                alignment = Pos.CENTER_RIGHT
            }
            canvases {
                backgroundColor= multi(Color.WHITE)
            }


            heading {
                fontSize = 3.em
                textFill = Color.WHITE
                fontWeight = FontWeight.BOLD
            }
            content {
                padding = box(25.px)
                button {
                    fontSize = 22.px
                }
            }
        }
    }
}
