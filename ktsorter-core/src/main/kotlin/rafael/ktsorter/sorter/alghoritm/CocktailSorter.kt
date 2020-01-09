package rafael.ktsorter.sorter.alghoritm

class CocktailSorter(pauseTime: Long) : Sorter(pauseTime, TYPE) {

    companion object {
        val TYPE = SortType.EXCHANGE
    }

    private fun moveToLeft(i: Int, right: Int, left: Int, values: IntArray): Int {
        var i1: Int = right
        while (i1 > left) {
            if (super.isLesser(values, i1, i1 - 1)) {
                super.swap(values, i1 - 1, i1)
            }
            i1--
        }
        return i1
    }

    private fun moveToRight(i: Int, right: Int, values: IntArray): Int {
        var i1 = i
        while (i1 < right) {
            if (super.isLesser(values, i1 + 1, i1)) {
                super.swap(values, i1, i1 + 1)
            }
            i1++
        }
        return i1
    }

    override fun process(values: IntArray): IntArray {
        var left = 0
        var right = values.size - 1

        while (left < right) {
            var i = left
            i = moveToRight(i, right, values)

            right --
            moveToLeft(i, right, left, values)

            left ++
        }

        return values
    }

}
