package rafael.ktsorter.util

typealias Observer = (String, Any?) -> Unit

interface Observable {
    val observers: MutableList<Observer>

    fun dataChanged(id: String, value: Any?) {
        observers.forEach { it(id, value) }
    }

}
