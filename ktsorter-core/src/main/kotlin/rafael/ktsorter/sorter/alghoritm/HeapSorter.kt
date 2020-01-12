package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Heapsort
 * https://gist.github.com/gyoshev/4038839
 */
class HeapSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Heap", SortType.SELECTION, AveragePerformance.N_LOG_N)
    }

    private tailrec fun maxHeapfy(values: IntArray, i: Int, length: Int) {
        val left = i * 2 + 1
        val right = i * 2 + 2
        var largest = i

        if (left < length && super.isLesser(values, largest, left)) {
            largest = left
        }

        if (right < length && super.isLesser(values, largest, right)) {
            largest = right
        }

        if (i == largest) {
            return
        }

        super.swap(values, i, largest)

        maxHeapfy(values, largest, length)
    }

    private fun heapfy(values: IntArray, lenght: Int) {
        for (i in lenght / 2 downTo 0) {
            this.maxHeapfy(values, i, lenght)
        }
    }

    internal fun sort(values: IntArray, begin: Int, endExclusive: Int) {
        this.heapfy(values, endExclusive - begin)

        for (i in values.lastIndex downTo 1) {
            super.swap(values, i, begin)
            this.maxHeapfy(values, begin, i - 1)
        }
        // Workaround!
        if (super.isLesser(values, begin + 2, begin + 1)) {
            super.swap(values, begin + 2, begin + 1)
        }
    }

    override fun process(values: IntArray): IntArray {
        sort(values, 0, values.size)

        return values
    }
}
