package rafael.ktsorter.sorter.events

import java.lang.RuntimeException

import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

internal class CounterListenerTest {

    private fun testWithoutStart(comparsions: Int, swaps: Int, event: SortEvent) {
        val listener = CounterListener()

        assertFailsWith(UninitializedPropertyAccessException::class, "onEvent") { listener.onEvent(event) }

        assertEquals(comparsions, listener.comparsions, "comparsions")
        assertEquals(swaps, listener.swaps, "swaps")
        assertFailsWith(RuntimeException::class, "t0") { listener.t0 }
        assertEquals(Duration.ZERO, listener.duration, "duration")
    }

    private fun testWithStart(comparsions: Int, swaps: Int, eventGenerator: () -> SortEvent) {
        val listener = CounterListener()
        val startEvent = StartEvent(emptyList())

        listener.onEvent(startEvent)
        listener.onEvent(eventGenerator())

        assertEquals(comparsions, listener.comparsions, "comparsions")
        assertEquals(swaps, listener.swaps, "swaps")
        assertSame(startEvent.date, listener.t0, "t0")
        assertEquals(Duration.ZERO, listener.duration)
    }

    @Test
    fun `Idle Event does not change properties`() {
        val listener = CounterListener()
        val event = IdleEvent("Test")

        listener.onEvent(event)

        assertEquals(0, listener.comparsions, "comparsions")
        assertEquals(0, listener.swaps, "swaps")
        assertFailsWith(RuntimeException::class, "t0") { listener.t0 }
        assertEquals(Duration.ZERO, listener.duration, "duration")
    }

    @Test
    fun `Start Event initialize t0`() {
        val listener = CounterListener()
        val event = StartEvent(emptyList())

        listener.onEvent(event)

        assertEquals(0, listener.comparsions, "comparsions")
        assertEquals(0, listener.swaps, "swaps")
        assertSame(event.date, listener.t0, "t0")
        assertEquals(Duration.ZERO, listener.duration, "duration")
    }

    @Test
    fun `Comparsion Event without Start `() {
        testWithoutStart(1, 0, ComparsionEvent(emptyList(), emptyList()))
    }

    @Test
    fun `Comparsion Event with Start `() {
        testWithStart(1, 0) { ComparsionEvent(emptyList(), emptyList()) }
    }

    @Test
    fun `Swap Event without Start `() {
        testWithoutStart(0, 1, SwapEvent(emptyList(), emptyList()))
    }

    @Test
    fun `Swap Event with Start `() {
        testWithStart(0, 1) { SwapEvent(emptyList(), emptyList()) }
    }

    @Test
    fun `Set Event without Start `() {
        testWithoutStart(0, 1, SwapEvent(emptyList(), emptyList()))
    }

    @Test
    fun `Set Event with Start `() {
        testWithStart(0, 1) { SwapEvent(emptyList(), emptyList()) }
    }

    @Test
    fun `End Event without Start `() {
        testWithoutStart(0, 0, EndEvent(emptyList()))
    }

    @Test
    fun `End Event with Start `() {
        testWithStart(0, 0) { EndEvent(emptyList()) }
    }

}
