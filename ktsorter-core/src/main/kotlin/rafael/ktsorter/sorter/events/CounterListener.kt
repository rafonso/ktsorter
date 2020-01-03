package rafael.ktsorter.sorter.events

import java.time.Duration
import java.time.LocalDateTime

class CounterListener : SortListener {

    private var _comparsions: Int = 0
    private var _swaps: Int = 0
    private lateinit var _t0: LocalDateTime
    private var _duration: Duration = Duration.ZERO

    val comparsions: Int
        get() = _comparsions

    val swaps: Int
        get() = _swaps

    val t0: LocalDateTime
        get() = _t0

    val duration: Duration
        get() = _duration


    override fun onEvent(event: SortEvent) {
        when (event) {
            is StartEvent      -> _t0 = event.date
            is ComparsionEvent -> _comparsions += 1
            is SwapEvent       -> _swaps += 1
            is SetEvent        -> _swaps += 1

        }
        if (event !is IdleEvent) {
            _duration = Duration.between(_t0, event.date)
        }
    }
}
