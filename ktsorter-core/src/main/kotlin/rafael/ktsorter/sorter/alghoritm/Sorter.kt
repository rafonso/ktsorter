package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.sorter.events.*

internal class InterruptionThrowable : Throwable()

abstract class Sorter(private val pauseTime: Long) {

    private val sortListeners = mutableListOf<SortListener>()

    private val wait: () -> Unit = if (pauseTime == 0L) ({ }) else ({ Thread.sleep(pauseTime) })

    private var verifyInterruption: () -> Unit = {}

    private fun notifyAll(event: SortEvent) {
        wait()
        sortListeners.forEach { observer -> observer.onEvent(event) }
    }

    protected fun isLesser(values: IntArray, pos1: Int, pos2: Int): Boolean {
        verifyInterruption()
        this.notifyAll(ComparsionEvent(values, pos1, pos2))

        return values[pos1] < values[pos2]
    }

    protected fun isLesserThan(values: IntArray, pos: Int, value: Int): Boolean {
        verifyInterruption()
        this.notifyAll(ComparsionEvent(values, pos))

        return values[pos] < value
    }

    protected fun swap(values: IntArray, pos1: Int, pos2: Int) {
        verifyInterruption()
        val (value1, value2) = Pair(values[pos1], values[pos2])
        values[pos1] = value2
        values[pos2] = value1

        this.notifyAll(SwapEvent(values, pos1, pos2))
    }

    protected fun set(values: IntArray, pos: Int, value: Int) {
        verifyInterruption()
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

    fun requestInteruption() {
        this.verifyInterruption = { throw InterruptionThrowable() }
    }

    fun run(values: IntArray) {
        try {
            this.notifyAll(StartEvent(values.toList()))
            val sortedValues = process(values)
            this.notifyAll(EndEvent(sortedValues.toList()))
        } catch (e: InterruptionThrowable) {
            this.notifyAll(InterruptionEvent(values.toList()))
        } catch (e: Exception) {
            this.notifyAll(ErrorEvent(e))
        }
    }

}
