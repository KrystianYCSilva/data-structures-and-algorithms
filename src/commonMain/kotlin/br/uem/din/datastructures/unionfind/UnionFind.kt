package br.uem.din.datastructures.unionfind

/**
 * Estrutura Union-Find (também conhecida como Disjoint Set Union — DSU).
 *
 * Mantém uma coleção de conjuntos disjuntos e suporta duas operações fundamentais:
 * - [find]: encontrar o representante do conjunto que contém um elemento.
 * - [union]: unir dois conjuntos em um só.
 *
 * Utiliza **compressão de caminho** (path compression) em [find] e **união por rank**
 * (union by rank) em [union], garantindo complexidade amortizada quase constante.
 *
 * Complexidades (amortizadas com compressão de caminho + união por rank):
 *
 * | Operação       | Complexidade         |
 * |----------------|----------------------|
 * | [find]         | O(α(n)) amortizado   |
 * | [union]        | O(α(n)) amortizado   |
 * | [connected]    | O(α(n)) amortizado   |
 * | Espaço         | O(n)                 |
 *
 * onde α é a função inversa de Ackermann (praticamente constante para qualquer entrada realista).
 *
 * @property size o número total de elementos (indexados de 0 até [size] - 1).
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 21 — Data Structures for Disjoint Sets.
 */
public class UnionFind(public val size: Int) {
    private val parent = IntArray(size) { it }
    private val rank = IntArray(size) { 0 }
    private var count = size

    /**
     * Retorna o número de conjuntos disjuntos atualmente mantidos.
     */
    public val numberOfSets: Int
        get() = count

    /**
     * Encontra o representante (raiz) do conjunto que contém o elemento [i].
     *
     * Aplica compressão de caminho: todos os nós no caminho até a raiz passam a
     * apontar diretamente para ela, acelerando consultas futuras.
     *
     * @param i o índice do elemento (0 ≤ i < [size]).
     * @return o índice do representante do conjunto.
     * @throws IllegalArgumentException se [i] estiver fora dos limites.
     */
    public fun find(i: Int): Int {
        if (i < 0 || i >= size) throw IllegalArgumentException("Index out of bounds: $i")
        if (parent[i] != i) {
            parent[i] = find(parent[i])
        }
        return parent[i]
    }

    /**
     * Une os conjuntos que contêm os elementos [i] e [j].
     *
     * Aplica união por rank: a raiz de menor rank é anexada à raiz de maior rank,
     * mantendo a árvore balanceada. Se os elementos já pertencem ao mesmo conjunto,
     * nenhuma operação é realizada.
     *
     * @param i o índice do primeiro elemento.
     * @param j o índice do segundo elemento.
     * @throws IllegalArgumentException se [i] ou [j] estiverem fora dos limites.
     */
    public fun union(i: Int, j: Int) {
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
     * Verifica se os elementos [i] e [j] pertencem ao mesmo conjunto.
     *
     * @param i o índice do primeiro elemento.
     * @param j o índice do segundo elemento.
     * @return `true` se ambos estão no mesmo conjunto, `false` caso contrário.
     * @throws IllegalArgumentException se [i] ou [j] estiverem fora dos limites.
     */
    public fun connected(i: Int, j: Int): Boolean {
        return find(i) == find(j)
    }
}
