package rafael.ktsorter.sorter.alghoritm

import rafael.ktsorter.util.Descriptable

enum class Sorters(override val description: String, val generator: (Long) -> Sorter) : Descriptable {
    // @formatter:off
    BUBBLE      ("Bobble"       , { BubbleSorter        (it)    }),
    COCKTAIL    ("Cocktail"     , { CocktailSorter      (it)    }),
    ODD_EVEN    ("Odd Even"     , { OddEvenSorter       (it)    }),
//    CIRCLE      ("Circle"       , { CircleSorter        (it)    }),
    GNOME       ("Gnome"        , { GnomeSorter         (it)    }),
    COMB        ("Comb"         , { CombSorter          (it)     }),
    SHELL       ("Shell"        , { ShellSorter         (it)     }),
    INSERTION   ("Insertion"    , { InsertionSorter     (it)     }),
    SELECTION   ("Selection"    , { SelectionSorter     (it)     }),
    QUICKSORT   ("Quick"        , { QuickSorter         (it)     }),
    QUICKINSERT ("Quick Insert" , { QuickInsertSorter   (it)     }),
    HEAP        ("Heap"         , { HeapSorter          (it)     }),
    // @formatter:on
}
