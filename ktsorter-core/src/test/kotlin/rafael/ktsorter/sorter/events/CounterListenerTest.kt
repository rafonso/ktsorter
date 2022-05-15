package rafael.ktsorter.sorter.events

import java.lang.Exception
import java.lang.RuntimeException

import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

private class CounterObserver(var comparsions: Int = 0, var swaps: Int = 0, var duration: Duration = Duration.ZERO) {

    fun onchange(id: String, value: Any?) {
        // @formatter:off
        when (id) {
            CounterListener.COMPARSIONS -> comparsions  = value as Int
            CounterListener.SWAPS       -> swaps        = value as Int
            CounterListener.DURATION    -> duration     = value as Duration
            else                        -> error("Invalid Event: $id -> $value")
        }
        // @formatter:on
    }

}


internal class CounterListenerTest {


    private fun testWithoutStart(comparsions: Int, swaps: Int, event: SortEvent) {
        val listener = CounterListener()
        val observer = CounterObserver()
        listener.observers.add(observer::onchange)

        assertFailsWith(UninitializedPropertyAccessException::class, "onEvent") { listener.onEvent(event) }

        assertEquals(comparsions, listener.comparsions, "comparsions")
        assertEquals(listener.comparsions, observer.comparsions, "observer.comparsions")
        assertEquals(swaps, listener.swaps, "swaps")
        assertEquals(listener.swaps, observer.swaps, "observer.swaps")
        assertFailsWith(RuntimeException::class, "t0") { listener.t0 }
        assert(listener.duration.toMillis() >= 0) {
            "duration: ${listener.duration}"
        }
        assertEquals(listener.duration, observer.duration, "observer.duration")
    }

    private fun testWithStart(comparsions: Int, swaps: Int, eventGenerator: () -> SortEvent) {
        val listener = CounterListener()
        val observer = CounterObserver()
        listener.observers.add(observer::onchange)
        val startEvent = StartEvent(emptyList())

        listener.onEvent(startEvent)
        listener.onEvent(eventGenerator())

        assertEquals(comparsions, listener.comparsions, "comparsions")
        assertEquals(listener.comparsions, observer.comparsions, "observer.comparsions")
        assertEquals(swaps, listener.swaps, "swaps")
        assertEquals(listener.swaps, observer.swaps, "observer.swaps")
        assertSame(startEvent.date, listener.t0, "t0")
        assert(listener.duration.toMillis() >= 0) {
            "duration: ${listener.duration}"
        }
        assertEquals(listener.duration, observer.duration, "observer.duration")
    }

    @Test
    fun `Idle Event does not change properties`() {
        val listener = CounterListener()
        val observer = CounterObserver()
        listener.observers.add(observer::onchange)
        val event = IdleEvent("Test")

        listener.onEvent(event)

        assertEquals(0, listener.comparsions, "comparsions")
        assertEquals(listener.comparsions, observer.comparsions, "observer.comparsions")
        assertEquals(0, listener.swaps, "swaps")
        assertEquals(listener.swaps, observer.swaps, "observer.swaps")
        assertFailsWith(RuntimeException::class, "t0") { listener.t0 }
        assert(listener.duration.toMillis() >= 0) {
            "duration: ${listener.duration}"
        }
        assertEquals(listener.duration, observer.duration, "observer.duration")
    }

    @Test
    fun `Start Event initialize t0`() {
        val listener = CounterListener()
        val observer = CounterObserver()
        listener.observers.add(observer::onchange)
        val event = StartEvent(emptyList())

        listener.onEvent(event)

        assertEquals(0, listener.comparsions, "comparsions")
        assertEquals(listener.comparsions, observer.comparsions, "observer.comparsions")
        assertEquals(0, listener.swaps, "swaps")
        assertEquals(listener.swaps, observer.swaps, "observer.swaps")
        assertSame(event.date, listener.t0, "t0")
        assert(listener.duration.toMillis() >= 0) {
            "duration: ${listener.duration}"
        }
        assertEquals(listener.duration, observer.duration, "observer.duration")
    }

    @Test
    fun `Comparsion Event without Start `() {
        testWithoutStart(1, 0, ComparsionEvent(intArrayOf(), 1, 2))
    }

    @Test
    fun `Comparsion Event with Start `() {
        testWithStart(1, 0) { ComparsionEvent(intArrayOf(), 1, 2) }
    }

    @Test
    fun `Swap Event without Start `() {
        testWithoutStart(0, 1, SwapEvent(intArrayOf(), 1, 2))
    }

    @Test
    fun `Swap Event with Start `() {
        testWithStart(0, 1) { SwapEvent(intArrayOf(), 1, 2) }
    }

    @Test
    fun `Set Event without Start `() {
        testWithoutStart(0, 1, SetEvent(intArrayOf(), 1))
    }

    @Test
    fun `Set Event with Start `() {
        testWithStart(0, 1) { SetEvent(intArrayOf(), 1) }
    }

    @Test
    fun `End Event without Start `() {
        testWithoutStart(0, 0, EndEvent(intArrayOf()))
    }

    @Test
    fun `End Event with Start `() {
        testWithStart(0, 0) { EndEvent(emptyList()) }
    }

    @Test
    fun `Error should have an Exception`() {
        val ex = Exception("Excetpion")
        val event = ErrorEvent(ex)

        testWithoutStart(0, 0, event)
    }

}
