package br.uem.din.datastructures.tree

/**
 * Árvore de Segmentos (Segment Tree) com propagação preguiçosa (Lazy Propagation).
 *
 * Estrutura de dados para responder consultas de agregação em intervalos (range queries)
 * e realizar atualizações em intervalos de forma eficiente. A árvore é representada
 * implicitamente em um array, onde o nó i tem filhos 2i e 2i+1.
 *
 * A propagação preguiçosa (lazy propagation) adia a aplicação de atualizações em massa,
 * propagando-as para os filhos somente quando necessário. Isso permite atualizações
 * em intervalos em O(log n) ao invés de O(n).
 *
 * Operações suportadas com a função de combinação fornecida:
 * - Consulta de intervalo (range query): soma, mínimo, máximo, etc.
 * - Atualização pontual (point update).
 * - Atualização de intervalo com lazy propagation (range update).
 *
 * Complexidades:
 * - Construção ([build]): O(n)
 * - [query]: O(log n)
 * - [update] (pontual): O(log n)
 * - [rangeUpdate]: O(log n)
 *
 * @param T o tipo dos elementos.
 * @param identity o elemento identidade da operação (0 para soma, ∞ para min, -∞ para max).
 * @param combine a função associativa de combinação (ex: soma, min, max).
 *
 * Referência: Bentley, J. L. "Solutions to Klee's Rectangle Problems" (1977);
 *             Cormen, T. H. et al. "Introduction to Algorithms", Problema 14-2;
 *             Halim, S. & Halim, F. "Competitive Programming 3", Cap. 2.4.3.
 */
public class SegmentTree<T>(
    private val identity: T,
    private val combine: (T, T) -> T
) {

    private var tree: MutableList<T> = mutableListOf()
    private var lazy: MutableList<T> = mutableListOf()
    private var n: Int = 0

    /**
     * Constrói a árvore de segmentos a partir de um array de valores.
     *
     * Aloca um array de tamanho 4n e constrói recursivamente a árvore
     * de baixo para cima.
     *
     * Complexidade: O(n).
     *
     * @param data o array de valores de entrada.
     */
    public fun build(data: List<T>) {
        n = data.size
        if (n == 0) return
        tree = MutableList(4 * n) { identity }
        lazy = MutableList(4 * n) { identity }
        build(data, 1, 0, n - 1)
    }

    private fun build(data: List<T>, node: Int, start: Int, end: Int) {
        if (start == end) {
            tree[node] = data[start]
            return
        }
        val mid = (start + end) / 2
        build(data, 2 * node, start, mid)
        build(data, 2 * node + 1, mid + 1, end)
        tree[node] = combine(tree[2 * node], tree[2 * node + 1])
    }

    /**
     * Consulta o resultado da agregação no intervalo [left, right] (inclusive).
     *
     * Utiliza a função [combine] para combinar os resultados parciais dos segmentos
     * que intersectam o intervalo consultado. Propaga atualizações pendentes (lazy)
     * conforme necessário.
     *
     * Complexidade: O(log n).
     *
     * @param left índice esquerdo do intervalo (0-indexed, inclusive).
     * @param right índice direito do intervalo (0-indexed, inclusive).
     * @return o resultado da agregação no intervalo.
     */
    public fun query(left: Int, right: Int): T {
        require(left in 0 until n && right in 0 until n && left <= right) {
            "Índices fora do intervalo válido [0, ${n - 1}]."
        }
        return query(1, 0, n - 1, left, right)
    }

    private fun query(node: Int, start: Int, end: Int, left: Int, right: Int): T {
        propagate(node, start, end)
        if (right < start || end < left) return identity
        if (left <= start && end <= right) return tree[node]
        val mid = (start + end) / 2
        return combine(
            query(2 * node, start, mid, left, right),
            query(2 * node + 1, mid + 1, end, left, right)
        )
    }

    /**
     * Atualiza o valor na posição [index] para [value].
     *
     * Complexidade: O(log n).
     *
     * @param index o índice do elemento a ser atualizado (0-indexed).
     * @param value o novo valor.
     */
    public fun update(index: Int, value: T) {
        require(index in 0 until n) { "Índice fora do intervalo válido [0, ${n - 1}]." }
        update(1, 0, n - 1, index, value)
    }

    private fun update(node: Int, start: Int, end: Int, index: Int, value: T) {
        propagate(node, start, end)
        if (start == end) {
            tree[node] = value
            return
        }
        val mid = (start + end) / 2
        if (index <= mid) {
            update(2 * node, start, mid, index, value)
        } else {
            update(2 * node + 1, mid + 1, end, index, value)
        }
        tree[node] = combine(tree[2 * node], tree[2 * node + 1])
    }

    /**
     * Atualiza todos os elementos no intervalo [left, right] com [value]
     * utilizando propagação preguiçosa (lazy propagation).
     *
     * O valor pendente é combinado com o existente via [combine] e propagado
     * para os filhos somente quando necessário. Esta implementação aplica
     * a atualização como combinação do valor lazy existente com o novo valor.
     *
     * Complexidade: O(log n).
     *
     * @param left índice esquerdo do intervalo (0-indexed, inclusive).
     * @param right índice direito do intervalo (0-indexed, inclusive).
     * @param value o valor a ser aplicado ao intervalo.
     */
    public fun rangeUpdate(left: Int, right: Int, value: T) {
        require(left in 0 until n && right in 0 until n && left <= right) {
            "Índices fora do intervalo válido [0, ${n - 1}]."
        }
        rangeUpdate(1, 0, n - 1, left, right, value)
    }

    private fun rangeUpdate(node: Int, start: Int, end: Int, left: Int, right: Int, value: T) {
        propagate(node, start, end)
        if (right < start || end < left) return
        if (left <= start && end <= right) {
            lazy[node] = value
            propagate(node, start, end)
            return
        }
        val mid = (start + end) / 2
        rangeUpdate(2 * node, start, mid, left, right, value)
        rangeUpdate(2 * node + 1, mid + 1, end, left, right, value)
        tree[node] = combine(tree[2 * node], tree[2 * node + 1])
    }

    /**
     * Propaga atualizações pendentes (lazy) do nó para seus filhos.
     *
     * @param node o índice do nó no array.
     * @param start início do intervalo representado pelo nó.
     * @param end fim do intervalo representado pelo nó.
     */
    private fun propagate(node: Int, start: Int, end: Int) {
        if (lazy[node] != identity) {
            tree[node] = combine(tree[node], lazy[node])
            if (start != end) {
                lazy[2 * node] = combine(lazy[2 * node], lazy[node])
                lazy[2 * node + 1] = combine(lazy[2 * node + 1], lazy[node])
            }
            lazy[node] = identity
        }
    }
}
