package br.com.leandroluce.algoritmos.datastructures.unionfind

/**
 * Union-Find data structure (also known as Disjoint Set Union - DSU).
 * Supports path compression and union by rank.
 */
class UnionFind(val size: Int) {
    private val parent = IntArray(size) { it }
    private val rank = IntArray(size) { 0 }
    private var count = size

    /**
     * Returns the number of disjoint sets.
     */
    val numberOfSets: Int
        get() = count

    /**
     * Finds the representative of the set containing element [i].
     * Applies path compression.
     */
    fun find(i: Int): Int {
        if (i < 0 || i >= size) throw IllegalArgumentException("Index out of bounds: $i")
        if (parent[i] != i) {
            parent[i] = find(parent[i]) // Path compression
        }
        return parent[i]
    }

    /**
     * Unites the sets containing elements [i] and [j].
     * Applies union by rank.
     */
    fun union(i: Int, j: Int) {
        val rootI = find(i)
        val rootJ = find(j)

        if (rootI != rootJ) {
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ
            } else if (rank[rootI] > rank[rootJ]) {
                parent[rootJ] = rootI
            } else {
                parent[rootJ] = rootI
                rank[rootI]++
            }
            count--
        }
    }

    /**
     * Checks if elements [i] and [j] belong to the same set.
     */
    fun connected(i: Int, j: Int): Boolean {
        return find(i) == find(j)
    }
}
