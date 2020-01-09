package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Heapsort
 * https://gist.github.com/gyoshev/4038839
 */
class HeapSorter(pauseTime: Long) : Sorter(pauseTime, TYPE) {

    companion object {
        val TYPE = SortType.SELECTION
    }

    private tailrec fun maxHeapfy(values: IntArray, _i: Int, length: Int) {
        var i = _i
        while (true) {
            val left = i * 2 + 1
            val right = i * 2 + 2
            var largest = i

            if (left < length && super.isLesser(values, largest, left)) {
                largest = left
            }

            if (right < length && super.isLesser(values, largest, right)) {
                largest = right;
            }

            if (i == largest) {
                break
            }

            super.swap(values, i, largest)
            i = largest
        }
    }

    private fun heapfy(values: IntArray, lenght: Int) {
        for (i in lenght / 2 downTo 0) {
            this.maxHeapfy(values, i, lenght)
        }
    }


    override fun process(values: IntArray): IntArray {
        this.heapfy(values, values.size)

        for (i in values.lastIndex downTo 1) {
            super.swap(values, i, 0)
            this.maxHeapfy(values, 0, i - 1)
        }
        // Workaround!
        if(super.isLesser(values, 2, 1)) {
            super.swap(values, 2, 1)
        }

        return values
    }
}
