package rafael.ktsorter.sorter.events

import java.time.LocalDateTime

enum class EventType(val isFinal: Boolean = false) {
    IDLE, START, COMPARSION, SWAP, SET, ENDED(true), ERROR(true)
}

sealed class SortEvent(val type: EventType) {
    val date: LocalDateTime = LocalDateTime.now()
}

abstract class BasicSortEvent(type: EventType) : SortEvent(type) {
    abstract val values: List<Int>
    abstract val positions: List<Int>
}

data class IdleEvent(val sortType: String) :
        SortEvent(EventType.IDLE)

data class StartEvent(override val values: List<Int>) :
        BasicSortEvent(EventType.START) {

    override val positions
        get() = emptyList<Int>()

}

data class ComparsionEvent(override val values: List<Int>, override val positions: List<Int>) :
        BasicSortEvent(EventType.COMPARSION) {
    constructor(values: IntArray, vararg pos: Int) : this(values.toList(), pos.toList())
}

data class SwapEvent(override val values: List<Int>, override val positions: List<Int>) :
        BasicSortEvent(EventType.SWAP) {
    constructor(values: IntArray, pos1: Int, pos2: Int) : this(values.toList(), listOf(pos1, pos2))
}

data class SetEvent(override val values: List<Int>, override val positions: List<Int>) :
        BasicSortEvent(EventType.SET) {
    constructor(values: IntArray, pos: Int) : this(values.toList(), listOf(pos))
}

data class EndEvent(override val values: List<Int>) :
        BasicSortEvent(EventType.ENDED) {
    constructor(values: IntArray) : this(values.toList())

    override val positions
        get() = emptyList<Int>()

}

data class ErrorEvent(val error: Exception) :
        SortEvent(EventType.ERROR)
