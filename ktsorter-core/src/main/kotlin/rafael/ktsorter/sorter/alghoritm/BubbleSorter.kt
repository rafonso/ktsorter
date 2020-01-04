package rafael.ktsorter.sorter.alghoritm

class BubbleSorter(pauseTime: Long) : Sorter(pauseTime) {

    override fun process(values: IntArray): IntArray {
        for (i in (values.size - 1) downTo 1) {
            for (j in 1..i) {
                if (isLesser(values, j, j - 1)) {
                    super.swap(values, j - 1, j)
                }
            }
        }
        return values
    }
}
