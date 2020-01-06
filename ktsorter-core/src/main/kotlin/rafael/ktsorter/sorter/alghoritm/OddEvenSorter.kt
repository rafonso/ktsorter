package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Odd%E2%80%93even_sort
 */
class OddEvenSorter(pauseTime: Long) : Sorter(pauseTime) {

    override tailrec fun process(values: IntArray): IntArray {
        var sorted = true

        for (i in 1 until values.size - 1 step 2) {
            if (super.isLesser(values, i + 1, i)) {
                super.swap(values, i + 1, i)
                sorted = false
            }
        }
        for (i in values.indices step 2) {
            if (super.isLesser(values, i + 1, i)) {
                super.swap(values, i + 1, i)
                sorted = false
            }
        }

        return if (sorted) values else process(values)
    }
}
