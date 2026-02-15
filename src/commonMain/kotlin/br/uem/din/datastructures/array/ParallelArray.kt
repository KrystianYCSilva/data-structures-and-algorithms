package br.uem.din.datastructures.array

/**
 * A demonstration of Parallel Arrays structure (Structure of Arrays - SoA).
 * 
 * Instead of an array of objects (Array<Student>), we use separate arrays for each field.
 * This can improve cache locality for processing specific fields (e.g., averaging scores).
 */
class ParallelArray(initialCapacity: Int = 10) {
    private var ids = IntArray(initialCapacity)
    private var names = Array<String?>(initialCapacity) { null }
    private var scores = DoubleArray(initialCapacity)
    
    var size = 0
        private set

    fun add(id: Int, name: String, score: Double) {
        if (size == ids.size) resize()
        ids[size] = id
        names[size] = name
        scores[size] = score
        size++
    }

    fun get(index: Int): Triple<Int, String, Double> {
        checkIndex(index)
        return Triple(ids[index], names[index] ?: "", scores[index])
    }
    
    fun getId(index: Int): Int {
        checkIndex(index)
        return ids[index]
    }
    
    fun getName(index: Int): String? {
        checkIndex(index)
        return names[index]
    }
    
    fun getScore(index: Int): Double {
        checkIndex(index)
        return scores[index]
    }

    private fun checkIndex(index: Int) {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private fun resize() {
        val newCapacity = ids.size * 2
        ids = ids.copyOf(newCapacity)
        names = names.copyOf(newCapacity)
        scores = scores.copyOf(newCapacity)
    }
}
