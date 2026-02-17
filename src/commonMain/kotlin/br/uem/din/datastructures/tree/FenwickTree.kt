package br.uem.din.datastructures.tree

/**
 * Árvore de Fenwick (Fenwick Tree / Binary Indexed Tree — BIT).
 *
 * Proposta por Fenwick (1994), esta estrutura fornece uma alternativa eficiente em espaço
 * à Árvore de Segmentos para consultas de soma de prefixos e atualizações pontuais.
 *
 * A ideia central é armazenar somas parciais em um array onde cada posição i é responsável
 * por um intervalo de tamanho determinado pelo bit menos significativo de i (lowbit).
 * A operação lowbit(i) = i AND (-i) extrai o bit menos significativo, que define
 * o comprimento do intervalo coberto por tree[i].
 *
 * Propriedades:
 * - Usa indexação 1-based internamente.
 * - Espaço: O(n) — apenas um array de tamanho n+1.
 * - Operações baseadas em manipulação de bits para navegação eficiente.
 *
 * Complexidades:
 * - [update]: O(log n)
 * - [prefixSum]: O(log n)
 * - [rangeSum]: O(log n)
 * - Construção a partir de array: O(n)
 *
 * @param size o número de elementos (posições de 0 a size-1).
 *
 * Referência: Fenwick, P. M. "A New Data Structure for Cumulative Frequency Tables" (1994);
 *             Halim, S. & Halim, F. "Competitive Programming 3", Cap. 2.4.4;
 *             Cormen, T. H. et al. "Introduction to Algorithms" — exercícios sobre BIT.
 */
public class FenwickTree(private val size: Int) {

    private val tree: LongArray = LongArray(size + 1)

    /**
     * Constrói a Fenwick Tree a partir de um array de valores iniciais.
     *
     * Utiliza o algoritmo de construção O(n) que propaga cada valor para seu
     * pai imediato, evitando a abordagem ingênua de n chamadas a [update].
     *
     * Complexidade: O(n).
     *
     * @param values o array de valores iniciais (0-indexed).
     */
    public fun build(values: LongArray) {
        require(values.size == size) { "O tamanho do array deve ser igual a $size." }
        for (i in values.indices) {
            tree[i + 1] = values[i]
        }
        for (i in 1..size) {
            val parent = i + (i and -i)
            if (parent <= size) {
                tree[parent] += tree[i]
            }
        }
    }

    /**
     * Adiciona [delta] ao valor na posição [index].
     *
     * Percorre a árvore de [index] até o final, atualizando todas as posições
     * responsáveis pelo intervalo que contém [index], utilizando a operação
     * lowbit para saltar ao próximo ancestral.
     *
     * Complexidade: O(log n).
     *
     * @param index o índice do elemento a ser atualizado (0-indexed).
     * @param delta o valor a ser adicionado.
     */
    public fun update(index: Int, delta: Long) {
        require(index in 0 until size) { "Índice fora do intervalo válido [0, ${size - 1}]." }
        var i = index + 1
        while (i <= size) {
            tree[i] += delta
            i += i and -i
        }
    }

    /**
     * Retorna a soma dos elementos no intervalo [0, index] (inclusive).
     *
     * Percorre a árvore de [index] até a raiz, acumulando as somas parciais
     * armazenadas em cada nó, utilizando a operação lowbit para navegar.
     *
     * Complexidade: O(log n).
     *
     * @param index o índice final do prefixo (0-indexed, inclusive).
     * @return a soma dos elementos de 0 a [index].
     */
    public fun prefixSum(index: Int): Long {
        require(index in 0 until size) { "Índice fora do intervalo válido [0, ${size - 1}]." }
        var sum = 0L
        var i = index + 1
        while (i > 0) {
            sum += tree[i]
            i -= i and -i
        }
        return sum
    }

    /**
     * Retorna a soma dos elementos no intervalo [left, right] (inclusive).
     *
     * Calculada como prefixSum(right) - prefixSum(left - 1).
     *
     * Complexidade: O(log n).
     *
     * @param left índice esquerdo do intervalo (0-indexed, inclusive).
     * @param right índice direito do intervalo (0-indexed, inclusive).
     * @return a soma dos elementos no intervalo.
     */
    public fun rangeSum(left: Int, right: Int): Long {
        require(left in 0 until size && right in 0 until size && left <= right) {
            "Índices fora do intervalo válido [0, ${size - 1}]."
        }
        return if (left == 0) prefixSum(right) else prefixSum(right) - prefixSum(left - 1)
    }

    /**
     * Retorna o valor individual na posição [index].
     *
     * Calculado como rangeSum(index, index).
     *
     * Complexidade: O(log n).
     *
     * @param index o índice do elemento (0-indexed).
     * @return o valor na posição [index].
     */
    public fun pointQuery(index: Int): Long {
        return rangeSum(index, index)
    }
}
