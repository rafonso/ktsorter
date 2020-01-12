package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.util.Descriptable

/**
 * Sorting algorithms
 */
enum class Sorters(val info: SortInfo, val generator: (Long) -> Sorter) : Descriptable {
    // @formatter:off
    BUBBLE      (BubbleSorter      .INFO, { BubbleSorter       (it)}),
    COCKTAIL    (CocktailSorter    .INFO, { CocktailSorter     (it)}),
    ODD_EVEN    (OddEvenSorter     .INFO, { OddEvenSorter      (it)}),
    CIRCLE      (CircleSorter      .INFO, { CircleSorter       (it)}),
    GNOME       (GnomeSorter       .INFO, { GnomeSorter        (it)}),
    COMB        (CombSorter        .INFO, { CombSorter         (it)}),
    SHELL       (ShellSorter       .INFO, { ShellSorter        (it)}),
    INSERTION   (InsertionSorter   .INFO, { InsertionSorter    (it)}),
    SELECTION   (SelectionSorter   .INFO, { SelectionSorter    (it)}),
    QUICKSORT   (QuickSorter       .INFO, { QuickSorter        (it)}),
    QUICKINSERT (QuickInsertSorter .INFO, { QuickInsertSorter  (it)}),
    HEAP        (HeapSorter        .INFO, { HeapSorter         (it)}),
    PANCAKE     (PancakeSorter     .INFO, { PancakeSorter      (it)}),
    MERGE       (MergeSorter       .INFO, { MergeSorter        (it)}),
    // @formatter:on
    ;

    override val description = info.name

}
