package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.sorter.events.*
import rafael.ktsorter.util.Observable
import rafael.ktsorter.util.Observer
import java.lang.Exception
import kotlin.properties.Delegates

abstract class Sorter(private val pauseTime: Long, val type: SortType) : Observable {

    companion object {
        const val RUNNING = "RUNNING"
    }

    private val sortListeners = mutableListOf<SortListener>()

    private var _running: Boolean by Delegates.observable(false) { _, _, newValue -> super.dataChanged(RUNNING, newValue) }

    private val wait: () -> Unit = if (pauseTime == 0L) ({ }) else ({ Thread.sleep(pauseTime) })

    val running: Boolean
        get() = _running

    private fun notifyAll(event: SortEvent) {
        wait()
        sortListeners.forEach { observer -> observer.onEvent(event) }
    }

    protected fun isLesser(values: IntArray, pos1: Int, pos2: Int): Boolean {
        this.notifyAll(ComparsionEvent(values, pos1, pos2))

        return values[pos1] < values[pos2]
    }

    protected fun isLesserThan(values: IntArray, pos: Int, value: Int): Boolean {
        this.notifyAll(ComparsionEvent(values, pos))

        return values[pos] < value
    }

    protected fun swap(values: IntArray, pos1: Int, pos2: Int) {
        val temp = values[pos1]
        values[pos1] = values[pos2]
        values[pos2] = temp

        this.notifyAll(SwapEvent(values, pos1, pos2))
    }

    protected fun set(values: IntArray, pos: Int, value: Int) {
        values[pos] = value

        this.notifyAll(SetEvent(values, pos))
    }

    protected abstract fun process(values: IntArray): IntArray

    fun subscribe(listener: SortListener) {
        sortListeners += listener
    }

    fun unsubscribe(listener: SortListener) {
        sortListeners -= listener
    }

    override val observers: MutableList<Observer> = mutableListOf()

    fun run(values: IntArray) {
        check(!_running) {
            "Sorter is already running"
        }
        try {
            _running = true
            this.notifyAll(StartEvent(values.toList()))
            val sortedValues = process(values)
            this.notifyAll(EndEvent(sortedValues.toList()))
        } catch (e: Exception) {
            this.notifyAll(ErrorEvent(e))
        } finally {
            _running = false
        }
    }

}
