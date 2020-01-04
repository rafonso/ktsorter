package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.sorter.events.*
import java.lang.Exception

abstract class Sorter(private val pauseTime: Long) {

    private val observers = mutableListOf<SortListener>()

    private val wait: () -> Unit = if (pauseTime == 0L) ({ }) else ({ Thread.sleep(pauseTime) })

    private fun notifyAll(event: SortEvent) {
        wait()
        observers.forEach { observer -> observer.onEvent(event) }
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
        observers += listener
    }

    fun unsubscribe(listener: SortListener) {
        observers -= listener
    }

    fun run(values: IntArray) {
        try {
            this.notifyAll(StartEvent(values.toList()))
            val sortedValues = process(values)
            this.notifyAll(EndEvent(sortedValues.toList()))
        } catch (e: Exception) {
            this.notifyAll(ErrorEvent(e))
        }
    }

}
