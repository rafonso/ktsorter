package rafael.ktsorter.sorter.alghoritm

/**
 * https://en.wikipedia.org/wiki/Selection_sort
 * https://khan4019.github.io/front-end-Interview-Questions/sort.html#selectionSort
 */
class SelectionSorter(pauseTime: Long) : Sorter(pauseTime) {

    companion object {
        val INFO = SortInfo("Selection", SortType.SELECTION, AveragePerformance.N2)
    }

    private tailrec fun getSelectedIndex(values: IntArray, j: Int, selectedIndex: Int): Int {
        if (j == values.size) {
            return selectedIndex
        }

        val nextIndex = if (super.isLesser(values, j, selectedIndex)) j else selectedIndex

        return getSelectedIndex(values, j + 1, nextIndex)
    }

    private tailrec fun sort(i: Int, values: IntArray): IntArray {
        if (i == values.size) {
            return values
        }

        val selectedIndex = getSelectedIndex(values, i + 1, i)
        if (i != selectedIndex) {
            super.swap(values, i, selectedIndex)
        }

        return sort(i + 1, values)
    }

    override fun process(values: IntArray): IntArray = sort(0, values)

}
