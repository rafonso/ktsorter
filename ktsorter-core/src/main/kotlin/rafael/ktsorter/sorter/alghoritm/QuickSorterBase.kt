package rafael.ktsorter.sorter.alghoritm

abstract class QuickSorterBase(pauseTime: Long) : Sorter(pauseTime) {

    private tailrec fun partition(values: IntArray, pivotValue: Int, end: Int, i: Int, partitionIndex: Int): Int {
        if (i >= end) {
            return partitionIndex
        }

        val nextPartitionIndex = if (super.isLesserThan(values, i, pivotValue)) {
            super.swap(values, i, partitionIndex)
            partitionIndex + 1
        } else {
            partitionIndex
        }

        return partition(values, pivotValue, end, i + 1, nextPartitionIndex)
    }

    internal open fun sort(values: IntArray, begin: Int, end: Int): IntArray {
        if (begin < end) {
            val partitionIndex = this.partition(values, values[end], end, begin, begin)
            super.swap(values, end, partitionIndex)

            this.sort(values, begin, partitionIndex - 1)
            this.sort(values, partitionIndex + 1, end)
        }

        return values
    }

    override fun process(values: IntArray): IntArray = sort(values, 0, values.lastIndex)

}
