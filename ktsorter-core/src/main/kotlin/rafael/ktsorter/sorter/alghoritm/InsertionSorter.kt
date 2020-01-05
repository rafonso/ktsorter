package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Insertion_sort
 * https://khan4019.github.io/front-end-Interview-Questions/sort.html#insertionSort
 */
class InsertionSorter(pauseTime: Long) : Sorter(pauseTime) {

    private tailrec fun findInsertionPos(values: IntArray, value: Int, j: Int): Int {
        if(j == 0 || super.isLesserThan(values, j - 1, value)) {
            return j
        }

        super.set(values, j, values[j - 1])

        return findInsertionPos(values, value, j - 1)
    }

    private tailrec fun sort(values: IntArray, i: Int): IntArray {
        if (i == values.size) {
            return values
        }

        val value = values[i]
        val j = findInsertionPos(values, value, i)
        super.set(values, j, value)

        return sort(values, i + 1)
    }

    override fun process(values: IntArray): IntArray = sort(values, 1)

}
