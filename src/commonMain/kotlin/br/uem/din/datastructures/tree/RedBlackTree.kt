package br.uem.din.datastructures.tree

/**
 * Árvore Rubro-Negra (Red-Black Tree) — árvore binária de busca autobalanceada.
 *
 * Inventada por Rudolf Bayer (1972) e popularizada por Guibas & Sedgewick (1978),
 * mantém propriedades de balanceamento através de coloração de nós (vermelho/preto)
 * e rotações, garantindo altura O(log n).
 *
 * Propriedades invariantes:
 * 1. Todo nó é vermelho ou preto
 * 2. A raiz é sempre preta
 * 3. Todas as folhas (NIL) são pretas
 * 4. Nós vermelhos têm apenas filhos pretos (sem dois vermelhos consecutivos)
 * 5. Todos os caminhos de um nó até suas folhas descendentes contêm o mesmo número de nós pretos
 *
 * | Operação | Complexidade |
 * |----------|--------------|
 * | insert   | O(log n)     |
 * | contains | O(log n)     |
 * | size     | O(1)         |
 * | isEmpty  | O(1)         |
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 13 — Red-Black Trees;
 *             Guibas, L. J. & Sedgewick, R. "A Dichromatic Framework for Balanced Trees" (1978).
 */
expect class RedBlackTree<T : Comparable<T>>() {
    fun insert(value: T): Boolean
    fun contains(value: T): Boolean
    fun size(): Int
    fun isEmpty(): Boolean
}
