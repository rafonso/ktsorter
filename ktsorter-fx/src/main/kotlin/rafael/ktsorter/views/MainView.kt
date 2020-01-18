package rafael.ktsorter.views

import javafx.application.Platform
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.shape.Shape
import javafx.scene.text.Font
import rafael.ktsorter.Styles
import rafael.ktsorter.numbergenerator.NumberGenerator
import rafael.ktsorter.sorter.alghoritm.Sorters
import rafael.ktsorter.sorter.events.*
import rafael.ktsorter.sorter.events.SortEvent
import rafael.ktsorter.util.Observer
import rafael.ktsorter.views.plot.Plotter
import rafael.ktsorter.views.plot.Plotters
import tornadofx.*
import java.io.PrintWriter
import java.io.StringWriter
import java.time.Duration
import java.util.*

private enum class RunningState {
    WAITING_DATA, READY_TO_RUN, RUNNING, CONCLUDED
}

class MainView : View("KTSorter"), SortListener, Observer {

    // @formatter:off
    private lateinit var pnlControls        : Pane
    private lateinit var cmbQuantity        : ComboBox<Limits>
    private lateinit var cmbSequenceType    : ComboBox<NumberGenerator>
    private lateinit var cmbExihibitionType : ComboBox<Plotters>
    private lateinit var cmbSortingType     : ComboBox<Sorters>
    private lateinit var cmbIntervalCycles  : ComboBox<Long>
    private lateinit var chbSound           : CheckBox
    private lateinit var btnGenerateNumbers : Button
    private lateinit var btnSort            : Button
    private lateinit var btnReset           : Button
    private lateinit var btnStop            : Button
    private lateinit var txfComparsions     : TextField
    private lateinit var txfSwaps           : TextField
    private lateinit var txfTime            : TextField
    private lateinit var sortPane           : Pane
    private lateinit var plotter            : Plotter
    // @formatter:on

    private val initialValues: ObjectProperty<IntArray> = SimpleObjectProperty<IntArray>().also { arr ->
        arr.onChange { newValue ->
            runningState.value =
                    if (newValue == null || newValue.isEmpty()) RunningState.WAITING_DATA
                    else RunningState.READY_TO_RUN
        }
    }

    private val runningState: ObjectProperty<RunningState> =
            SimpleObjectProperty<RunningState>(RunningState.WAITING_DATA)

    private var counterListener: CounterListener? = null

    private val imageExihibitionSelected = SimpleBooleanProperty(false).apply {
        onChange {
            val imageSelector: (Plotters) -> Boolean = if (this.value) { _ -> true } else { p -> !p.isImage }
            cmbExihibitionType.items = Plotters.values().filter(imageSelector).toList().observable()
        }
    }


    init {
        super.primaryStage.isResizable = false
    }

    override val root = borderpane {
        addClass(Styles.mainScreen)
        left {
            pnlControls = form {
                vbox(10, Pos.CENTER) {
                    fieldset {
                        field("Quantity") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbQuantity = combobox {
                                items = limitsValues.observable()
                                converter = DescriptableConverter(limitsValues)
                                onAction = EventHandler {
                                    imageExihibitionSelected.value = value.allowedForImages
                                }
                            }
                            label.labelFor = cmbQuantity
                        }
                        field("Sequence Type") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbSequenceType = combobox {
                                items = NumberGenerator.values().toList().observable()
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
                                items = Sorters.values()
                                        .sortedWith(compareBy(
                                                { -it.info.performance.ordinal },
                                                { it.info.type },
                                                { it.info.name }
                                        ))
                                        .toList().observable()
                                converter = DescriptableConverter(Sorters.values())
                            }
                            label.labelFor = cmbSortingType
                        }
                        field("Interval Cycle (ms)") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            cmbIntervalCycles = combobox {
                                items = listOf(0L, 1L, 2L, 5L, 10L, 20L, 50L).observable()
                            }
                            label.labelFor = cmbIntervalCycles
                        }
                        field("Activate Sound") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            chbSound = checkbox {
                                isDisable = true
                            }
                        }
                        disableProperty().bind(runningState.isEqualTo(RunningState.RUNNING))
                    }
                    separator {
                        addClass(Styles.pad)
                    }
                    btnGenerateNumbers = button("Generate Numbers") {
                        addClass(Styles.buttons)
                        onAction = EventHandler { generateInitialValues() }
                        disableProperty().bind(runningState.isEqualTo(RunningState.RUNNING))
                    }
                    btnSort = button("Sort") {
                        addClass(Styles.buttons)
                        onAction = EventHandler { startSorting() }
                        disableProperty().bind(runningState.isNotEqualTo(RunningState.READY_TO_RUN))
                    }
                    btnReset = button("Reset") {
                        addClass(Styles.buttons)
                        onAction = EventHandler { initComponents() }
                        disableProperty().bind(runningState.isEqualTo(RunningState.RUNNING))
                    }
                    btnStop = button("Stop") {
                        addClass(Styles.buttons)
                        disableProperty().bind(runningState.isNotEqualTo(RunningState.RUNNING))
                    }
                    separator {
                        addClass(Styles.pad)
                    }
                    fieldset {
                        field("Comparsions") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfComparsions = textfield {
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                        field("Swaps") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfSwaps = textfield {
                                isEditable = false
                                addClass(Styles.texts)
                            }
                        }
                        field("Time") {
                            addClass(Styles.controlsFields)
                            label.addClass(Styles.labels)
                            txfTime = textfield {
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
        // @formatter:off
        cmbQuantity         .value      = limitsValues.filter { it.quantity > 50 }.minBy { it.quantity }
        cmbSequenceType     .value      = cmbSequenceType       .items[0]
        cmbExihibitionType  .value      = cmbExihibitionType    .items[0]
        cmbSortingType      .value      = cmbSortingType        .items[0]
        cmbIntervalCycles   .value      = 1
        chbSound            .isSelected = true
        txfComparsions      .text       = ""
        txfSwaps            .text       = ""
        txfTime             .text       = ""
        // @formatter:on
        sortPane.children.removeIf { n -> n is Shape }
    }


    private fun generateInitialValues() {
        initialValues.value = cmbSequenceType.selectedItem!!.generate(cmbQuantity.selectedItem!!.quantity)
        exihibitionTypeChanged()
    }

    private fun exihibitionTypeChanged() {
        if (initialValues.isNotNull.value && cmbExihibitionType.value != null) {
            plotter = cmbExihibitionType.value!!.createPlotter(sortPane, initialValues.value!!, cmbQuantity.value)
        }
    }

    private fun startSorting() {
        val sorter = cmbSortingType.value.generator(cmbIntervalCycles.value)
        sorter.subscribe(this)
        btnStop.onAction = EventHandler {
            sorter.requestInteruption()
            btnStop.onAction = null
        }

        counterListener = CounterListener().also {
            sorter.subscribe(it)
            it.observers.add(this::invoke)
        }

        Thread(SorterTask(sorter, plotter), "%s-%tT".format(cmbSortingType.value, Date())).start()
        runningState.value = RunningState.RUNNING
    }

    override fun onEvent(event: SortEvent) {
        Platform.runLater {
            when (event) {
                is ErrorEvent        -> {
                    showError(event.error)
                    runningState.value = RunningState.CONCLUDED
                }
                is EndEvent          -> {
                    runningState.value = RunningState.CONCLUDED
                }
                is InterruptionEvent -> {
                    txfComparsions.text = "Interrupted by User"
                    txfTime.text = ""
                    txfSwaps.text = ""
                    runningState.value = RunningState.CONCLUDED
                }
            }
        }
    }


    override fun invoke(id: String, value: Any?) {
        Platform.runLater {
            when (id) {
                CounterListener.COMPARSIONS -> txfComparsions.text = (value as Int).toString()
                CounterListener.DURATION    -> {
                    val duration = (value as Duration)
                    txfTime.text = "%02d:%02d.%03d".format(
                            duration.toMinutesPart(),
                            duration.toSecondsPart(),
                            duration.toMillisPart()
                    )
                }
                CounterListener.SWAPS       -> txfSwaps.text = (value as Int).toString()
            }
        }
    }

    private fun showError(error: Exception) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        error.printStackTrace(pw)
        val stackTrace = sw.toString()

        Alert(Alert.AlertType.ERROR).also {
            it.title = "Error while running ${cmbSortingType.value.description}"
            it.headerText = if (error.message == null) "" else error.message
            it.initOwner(super.primaryStage)
            it.dialogPane.content = vbox {
                label { text = "Stack Trace:" }
                textarea {
                    text = stackTrace
                    font = Font.font("monospaced")
                    vgrow = Priority.ALWAYS
                }
            }
            it.isResizable = true
        }.showAndWait()
    }

}

