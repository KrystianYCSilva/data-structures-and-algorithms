package br.uem.din.datastructures.set

/**
 * Multiset (também conhecido como Bag) — coleção que permite elementos duplicados
 * e rastreia a contagem de cada elemento.
 *
 * Diferente de um conjunto (Set) convencional, um Multiset mantém a multiplicidade
 * de cada elemento. Internamente, utiliza um HashMap para mapear cada elemento
 * distinto à sua contagem, proporcionando operações eficientes.
 *
 * Complexidades:
 * - [add]: O(1) amortizado
 * - [remove]: O(1) amortizado
 * - [contains]: O(1)
 * - [count]: O(1)
 * - [distinctElements]: O(k), onde k é o número de elementos distintos
 * - Espaço: O(k), onde k é o número de elementos distintos
 *
 * @param T o tipo dos elementos armazenados.
 *
 * Referência: Knuth, D. E. "The Art of Computer Programming", Vol. 2 — Seminumerical Algorithms;
 *             Blizard, W. D. "Multiset Theory" (1989), Notre Dame Journal of Formal Logic, 30(1).
 */
public class Multiset<T> : MutableMultiset<T> {

    private val counts: HashMap<T, Int> = HashMap()

    /**
     * Número total de elementos no multiset, incluindo duplicatas.
     *
     * Por exemplo, se o multiset contém {a, a, b}, o tamanho é 3.
     */
    public override var size: Int = 0
        private set

    /**
     * Adiciona uma ou mais ocorrências de um elemento ao multiset.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser adicionado.
     * @param occurrences o número de ocorrências a adicionar (padrão: 1).
     * @throws IllegalArgumentException se [occurrences] for menor que 1.
     */
    public override fun add(element: T, occurrences: Int) {
        require(occurrences >= 1) { "Número de ocorrências deve ser >= 1, recebido: $occurrences" }
        counts[element] = (counts[element] ?: 0) + occurrences
        size += occurrences
    }

    /**
     * Remove uma ou mais ocorrências de um elemento do multiset.
     *
     * Se o número de ocorrências a remover for maior ou igual à contagem atual,
     * o elemento é completamente removido do multiset.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param element o elemento a ser removido.
     * @param occurrences o número de ocorrências a remover (padrão: 1).
     * @return `true` se o elemento existia no multiset, `false` caso contrário.
     * @throws IllegalArgumentException se [occurrences] for menor que 1.
     */
    public override fun remove(element: T, occurrences: Int): Boolean {
        require(occurrences >= 1) { "Número de ocorrências deve ser >= 1, recebido: $occurrences" }
        val currentCount = counts[element] ?: return false
        val newCount = currentCount - occurrences
        if (newCount <= 0) {
            counts.remove(element)
            size -= currentCount
        } else {
            counts[element] = newCount
            size -= occurrences
        }
        return true
    }

    /**
     * Retorna a contagem (multiplicidade) de um elemento no multiset.
     *
     * Complexidade: O(1).
     *
     * @param element o elemento cuja contagem será consultada.
     * @return o número de ocorrências do elemento, ou 0 se não existir.
     */
    public override fun count(element: T): Int = counts[element] ?: 0

    /**
     * Verifica se o multiset contém pelo menos uma ocorrência do elemento.
     *
     * Complexidade: O(1).
     *
     * @param element o elemento a ser verificado.
     * @return `true` se o elemento existir no multiset, `false` caso contrário.
     */
    public override fun contains(element: T): Boolean = counts.containsKey(element)

    /**
     * Retorna o conjunto de elementos distintos no multiset (sem duplicatas).
     *
     * Complexidade: O(k), onde k é o número de elementos distintos.
     *
     * @return um [Set] contendo os elementos distintos.
     */
    public override fun distinctElements(): Set<T> = counts.keys.toSet()

    /**
     * Retorna o número de elementos distintos no multiset.
     *
     * @return a quantidade de elementos únicos.
     */
    public override val distinctCount: Int
        get() = counts.size

    /**
     * Verifica se o multiset está vazio.
     *
     * @return `true` se o multiset não contiver elementos, `false` caso contrário.
     */
    public override fun isEmpty(): Boolean = size == 0

    /**
     * Remove todas as ocorrências de todos os elementos do multiset.
     */
    public override fun clear() {
        counts.clear()
        size = 0
    }

    /**
     * Retorna representação textual do multiset no formato `{elemento×contagem, ...}`.
     *
     * @return string formatada com os elementos e suas multiplicidades.
     */
    public override fun toString(): String {
        return counts.entries.joinToString(prefix = "{", postfix = "}") { "${it.key}×${it.value}" }
    }
}
