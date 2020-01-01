package rafael.ktsorter.views

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.shape.Shape
import rafael.ktsorter.Styles
import rafael.ktsorter.numbergenerator.NumberGenerator
import rafael.ktsorter.views.plot.Limits
import rafael.ktsorter.views.plot.Plotter
import rafael.ktsorter.views.plot.Plotters
import rafael.ktsorter.views.plot.limitsValues
import tornadofx.*

class MainView : View("KTSorter") {

    private lateinit var pnlControls: Pane
    private lateinit var cmbQuantity: ComboBox<Limits>
    private lateinit var cmbSequenceType: ComboBox<NumberGenerator>
    private lateinit var cmbExihibitionType: ComboBox<Plotters>
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

    private var initialValues: ObjectProperty<IntArray> = SimpleObjectProperty<IntArray>()

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
                                items = limitsValues.observable()
//                                value = limitsValues.find { it.quantity == 50 }
                                converter =
                                    DescriptableConverter(limitsValues)
                            }
                            label.labelFor = cmbQuantity
                        }
                        field("Sequence Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbSequenceType = combobox {
                                items = NumberGenerator.values().toList().observable()
//                                value = items[0]
                                converter =
                                    DescriptableConverter(NumberGenerator.values())
                            }
                            label.labelFor = cmbSequenceType
                        }
                        field("Exihibition Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbExihibitionType = combobox {
                                items = Plotters.values().toList().observable()
//                                value = items[0]
                                converter = DescriptableConverter(Plotters.values())
                                onAction = EventHandler {
                                    exihibitionTypeChanged()
                                }
                            }
                            label.labelFor = cmbExihibitionType
                        }
                        field("Sorting Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbSortingType = combobox {
                                items = listOf(
                                    "Bubble",
                                    "Cocktail",
                                    "Pancake",
                                    "Selection",
                                    "Insertion",
                                    "Circle"
                                ).observable()
                            }
                            label.labelFor = cmbSortingType
                        }
                        field("Interval Cycle (ms)") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbIntervalCycles = combobox {
                                items = listOf(0, 1, 2, 5, 10, 20, 50).observable()
//                                value = 10
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
                        action(this@MainView::generateInitialValues)
                    }
                    btnSort = button {
                        text = "Sort"
                        addClass(Styles.buttons)
                        disableProperty().bind(initialValues.isNull)
                    }
                    btnReset = button {
                        text = "Reset"
                        addClass(Styles.buttons)
                        onAction = EventHandler { initComponents() }
                    }
                    separator {
                        addClass(Styles.pad)
                    }
                    fieldset {
                        field("Comparsions") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfComparsions = textfield {
                                //                                text = "9999"
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                        field("Swaps") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfSwaps = textfield {
                                //                                text = "9999"
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                        field("Time") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfTime = textfield {
                                //                                text = "9999"
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
        initComponents()
    }

    private fun initComponents() {
        initialValues.value = null

        cmbQuantity.value = limitsValues.find { it.quantity == 50 }
        cmbSequenceType.value = cmbSequenceType.items[0]
        cmbExihibitionType.value = cmbExihibitionType.items[0]
        cmbSortingType.value = cmbSortingType.items[0]
        cmbIntervalCycles.value = cmbIntervalCycles.items.find { it == 10 }
        txfComparsions.text = null
        txfSwaps.text = null
        txfTime.text = null

        sortPane.children.removeIf { n -> n is Shape }
    }


    private fun generateInitialValues() {
        initialValues.value = cmbSequenceType.selectedItem !!.generate(cmbQuantity.selectedItem !!.quantity)
        exihibitionTypeChanged()
    }

    private fun exihibitionTypeChanged() {
        if (initialValues.isNotNull.value && cmbExihibitionType.value != null) {
            plotter = cmbExihibitionType.value !!.createPlotter(sortPane, initialValues.value !!, cmbQuantity.value)
        }
    }
}

