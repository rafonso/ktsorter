package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.util.Descriptable

/**
 * Type sorting algorithm according Wikipedia site: https://en.wikipedia.org/wiki/Sorting_algorithm
 */
enum class SortType {
    EXCHANGE, SELECTION, INSERTION, MERGE, DISTRIBUTION, CONCURRENT, HYBRID, OTHER
}

/**
 * Sorting algorithms
 */
enum class Sorters(override val description: String, type: SortType , val generator: (Long) -> Sorter) : Descriptable {
    // @formatter:off
    BUBBLE      ("Bobble"       , BubbleSorter      .TYPE, { BubbleSorter       (it)}),
    COCKTAIL    ("Cocktail"     , CocktailSorter    .TYPE, { CocktailSorter     (it)}),
    ODD_EVEN    ("Odd Even"     , OddEvenSorter     .TYPE, { OddEvenSorter      (it)}),
//  CIRCLE      ("Circle"       , CircleSorter      .TYPE, { CircleSorter       (it)}),
    GNOME       ("Gnome"        , GnomeSorter       .TYPE, { GnomeSorter        (it)}),
    COMB        ("Comb"         , CombSorter        .TYPE, { CombSorter         (it)}),
    SHELL       ("Shell"        , ShellSorter       .TYPE, { ShellSorter        (it)}),
    INSERTION   ("Insertion"    , InsertionSorter   .TYPE, { InsertionSorter    (it)}),
    SELECTION   ("Selection"    , SelectionSorter   .TYPE, { SelectionSorter    (it)}),
    QUICKSORT   ("Quick"        , QuickSorter       .TYPE, { QuickSorter        (it)}),
    QUICKINSERT ("Quick Insert" , QuickInsertSorter .TYPE, { QuickInsertSorter  (it)}),
    HEAP        ("Heap"         , HeapSorter        .TYPE, { HeapSorter         (it)}),
    // @formatter:on
}
