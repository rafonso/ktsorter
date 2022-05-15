package rafael.ktsorter.sorter.events

import rafael.ktsorter.util.Observable
import rafael.ktsorter.util.Observer
import java.time.Duration
import java.time.LocalDateTime
import kotlin.properties.Delegates

class CounterListener : SortListener, Observable {

    // @formatter:off
    companion object {
        const val COMPARSIONS = "COMPARSIONS"
        const val SWAPS = "SWAPS"
        const val DURATION = "DURATION"
    }
    // @formatter:on

    // @formatter:off
    private var _comparsions: Int       by Delegates.observable(0)              { _, _, newValue ->
        super.dataChanged(COMPARSIONS, newValue)
    }
    private var _swaps      : Int       by Delegates.observable(0)              { _, _, newValue ->
        super.dataChanged(SWAPS, newValue)
    }
    private var _duration   : Duration  by Delegates.observable(Duration.ZERO)  { _, _, newValue ->
        super.dataChanged(DURATION, newValue)
    }
    // @formatter:on

    private lateinit var _t0: LocalDateTime

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
            is ComparsionEvent -> _comparsions++
            is SwapEvent       -> _swaps++
            is SetEvent        -> _swaps++
            else               -> {}
        }
        if (event !is IdleEvent) {
            _duration = Duration.between(_t0, event.date)
        }
    }

    override val observers: MutableList<Observer> = mutableListOf()

}
