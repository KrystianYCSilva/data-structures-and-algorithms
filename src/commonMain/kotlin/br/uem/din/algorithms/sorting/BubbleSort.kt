package br.uem.din.algorithms.sorting

fun <T : Comparable<T>> bubbleSort(list: MutableList<T>) {
    val n = list.size
    for (i in 0 until n - 1) {
        for (j in 0 until n - i - 1) {
            if (list[j] > list[j + 1]) {
                val temp = list[j]
                list[j] = list[j + 1]
                list[j + 1] = temp
            }
        }
    }
}
