package rafael.ktsorter.sorter.alghoritm

abstract class QuickSorterBase(pauseTime: Long) : Sorter(pauseTime) {

    private tailrec fun partition(values: IntArray, pivotValue: Int, right: Int, i: Int, partitionIndex: Int): Int {
        if (i >= right) {
            return partitionIndex
        }

        val nextPartitionIndex = if (super.isLesserThan(values, i, pivotValue)) {
            super.swap(values, i, partitionIndex)
            partitionIndex + 1
        } else {
            partitionIndex
        }

        return partition(values, pivotValue, right, i + 1, nextPartitionIndex)
    }

    internal open fun sort(values: IntArray, left: Int, right: Int): IntArray {
        if (left < right) {
            val partitionIndex = this.partition(values, values[right], right, left, left)
            super.swap(values, right, partitionIndex)

            this.sort(values, left, partitionIndex - 1)
            this.sort(values, partitionIndex + 1, right)
        }

        return values
    }

    override fun process(values: IntArray): IntArray = sort(values, 0, values.lastIndex)

}
