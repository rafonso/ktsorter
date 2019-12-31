package rafael.ktsorter.views

import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import rafael.ktsorter.Styles
import rafael.ktsorter.numbergenerator.NumberGenerator
import rafael.ktsorter.views.plot.Limits
import rafael.ktsorter.views.plot.Plotter
import rafael.ktsorter.views.plot.limitsValues
import tornadofx.*

class MainView : View("KTSorter") {

    private lateinit var pnlControls: Pane
    private lateinit var cmbQuantity: ComboBox<Limits>
    private lateinit var cmbSequenceType: ComboBox<NumberGenerator>
    private lateinit var cmbExihibitionType: ComboBox<String>
    private lateinit var cmbSortingType: ComboBox<String>
    private lateinit var cmbIntervalCycles: ComboBox<Int>
    private lateinit var chbSound: CheckBox
    private lateinit var btnGenerateNumbers: Button
    private lateinit var btnSort: Button
    private lateinit var btnReset: Button
    private lateinit var txfComparsions: TextField
    private lateinit var txfSwaps: TextField
    private lateinit var txfTime: TextField
    private lateinit var sortPane: Pane
    private lateinit var plotter: Plotter

    init {
        super.primaryStage.isResizable = false
    }

    override val root = borderpane {
        addClass(Styles.mainScreen)
        left {
            pnlControls = form {
                vbox(spacing = 10, alignment = Pos.CENTER) {
                    fieldset {
                        field("Quantity") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbQuantity = combobox {
                                items = FXCollections.observableArrayList(limitsValues)
                                value = limitsValues.find { it.quantity == 50 }
                                converter = LimitsConverter()
                            }
                            label.labelFor = cmbQuantity
                        }
                        field("Sequence Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbSequenceType = combobox {
                                items = NumberGenerator.values().toList().observable()
                                value = items[0]
                            }
                            label.labelFor = cmbSequenceType
                        }
                        field("Exihibition Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbExihibitionType = combobox {
                                items = FXCollections.observableArrayList("Spectre", "Points")
                            }
                            label.labelFor = cmbExihibitionType
                        }
                        field("Sorting Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbSortingType = combobox {
                                items = FXCollections.observableArrayList(
                                    "Bubble",
                                    "Cocktail",
                                    "Pancake",
                                    "Selection",
                                    "Insertion",
                                    "Circle"
                                )
                            }
                            label.labelFor = cmbSortingType
                        }
                        field("Interval Cycle (ms)") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbIntervalCycles = combobox {
                                items = FXCollections.observableArrayList(0, 1, 2, 5, 10, 20, 50)
                                value = 10
                            }
                            label.labelFor = cmbIntervalCycles
                        }
                        field("Ativar som") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            chbSound = checkbox {
                                isSelected = true
                                isDisable = true
                            }
                        }
                    }
                    separator {
                        addClass(Styles.pad)
                    }
                    btnGenerateNumbers = button {
                        text = "Generate Number"
                        addClass(Styles.buttons)
                        action {
                            //                            val values = cmbSequenceType.selectedItem!!.generate(cmbQuantity.selectedItem!!)
                            plotter = Plotter(
                                sortPane,
                                cmbSequenceType.selectedItem!!.generate(cmbQuantity.selectedItem!!.quantity),
                                cmbQuantity.value
                            )
                        }
                    }
                    btnSort = button {
                        text = "Sort"
                        addClass(Styles.buttons)
                    }
                    btnReset = button {
                        text = "Reset"
                        addClass(Styles.buttons)
                    }
                    separator {
                        addClass(Styles.pad)
                    }
                    fieldset {
                        field("Comparsions") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfComparsions = textfield {
                                text = "9999"
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                        field("Swaps") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfSwaps = textfield {
                                text = "9999"
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                        field("Time") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfTime = textfield {
                                text = "9999"
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                    }
                }
            }
        }
        center {
            sortPane = pane {
                addClass(Styles.canvases)
            }
        }
    }
}

