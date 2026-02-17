package br.uem.din.datastructures.hash

/**
 * Tabela hash com encadeamento separado (Separate Chaining Hash Table).
 *
 * Resolve colisões armazenando múltiplos pares chave-valor em listas encadeadas
 * (buckets) associadas a cada posição da tabela. Cada bucket é implementado como
 * um [MutableList] de pares chave-valor.
 *
 * A tabela redimensiona automaticamente (rehash) quando o fator de carga excede [maxLoadFactor].
 *
 * Complexidades (esperadas, assumindo hash uniforme simples):
 *
 * | Operação   | Melhor caso | Caso médio | Pior caso |
 * |------------|:-----------:|:----------:|:---------:|
 * | [put]      | O(1)        | O(1 + α)   | O(n)      |
 * | [get]      | O(1)        | O(1 + α)   | O(n)      |
 * | [remove]   | O(1)        | O(1 + α)   | O(n)      |
 * | [contains] | O(1)        | O(1 + α)   | O(n)      |
 * | Espaço     |             | O(n + m)   |           |
 *
 * Onde α = n/m é o fator de carga, n o número de elementos e m a capacidade da tabela.
 *
 * @param K o tipo das chaves.
 * @param V o tipo dos valores.
 * @param initialCapacity capacidade inicial da tabela (padrão: 16).
 * @param maxLoadFactor fator de carga máximo antes do rehash (padrão: 0.75).
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 11 — Hash Tables (Chaining).
 */
public class SeparateChainingHashTable<K : Any, V>(
    initialCapacity: Int = 16,
    private val maxLoadFactor: Double = 0.75
) : MutableOpenHashTable<K, V> {

    private data class Entry<K, V>(val key: K, var value: V)

    private var capacity: Int = initialCapacity.coerceAtLeast(4)
    private var buckets: Array<MutableList<Entry<K, V>>?> = arrayOfNulls(capacity)

    /**
     * Número de pares chave-valor armazenados na tabela.
     */
    public override var size: Int = 0
        private set

    /**
     * Insere ou atualiza o par chave-valor na tabela.
     *
     * Se a chave já existir, o valor é atualizado. Caso contrário, um novo par é inserido
     * no bucket correspondente. Realiza rehash se o fator de carga exceder [maxLoadFactor]
     * após a inserção.
     *
     * Complexidade esperada: O(1 + α).
     *
     * @param key a chave a ser inserida ou atualizada.
     * @param value o valor associado à chave.
     */
    public override fun put(key: K, value: V) {
        if ((size + 1).toDouble() / capacity > maxLoadFactor) {
            resize(capacity * 2)
        }
        val index = bucketIndex(key)
        val bucket = buckets[index] ?: mutableListOf<Entry<K, V>>().also { buckets[index] = it }
        for (entry in bucket) {
            if (entry.key == key) {
                entry.value = value
                return
            }
        }
        bucket.add(Entry(key, value))
        size++
    }

    /**
     * Recupera o valor associado à chave especificada.
     *
     * Complexidade esperada: O(1 + α).
     *
     * @param key a chave a ser procurada.
     * @return o valor associado à chave, ou `null` se a chave não existir.
     */
    public override fun get(key: K): V? {
        val index = bucketIndex(key)
        val bucket = buckets[index] ?: return null
        for (entry in bucket) {
            if (entry.key == key) return entry.value
        }
        return null
    }

    /**
     * Remove o par chave-valor associado à chave especificada.
     *
     * Complexidade esperada: O(1 + α).
     *
     * @param key a chave a ser removida.
     * @return o valor removido, ou `null` se a chave não existir.
     */
    public override fun remove(key: K): V? {
        val index = bucketIndex(key)
        val bucket = buckets[index] ?: return null
        val iterator = bucket.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (entry.key == key) {
                iterator.remove()
                size--
                return entry.value
            }
        }
        return null
    }

    /**
     * Verifica se a tabela contém a chave especificada.
     *
     * Complexidade esperada: O(1 + α).
     *
     * @param key a chave a ser verificada.
     * @return `true` se a chave existir na tabela, `false` caso contrário.
     */
    public override fun contains(key: K): Boolean = get(key) != null

    /**
     * Calcula o índice do bucket para a chave especificada.
     *
     * @param key a chave cujo índice será calculado.
     * @return o índice do bucket na tabela.
     */
    private fun bucketIndex(key: K): Int = (key.hashCode() and 0x7FFFFFFF) % capacity

    /**
     * Redimensiona a tabela para a nova capacidade, reinserindo todos os elementos.
     *
     * @param newCapacity a nova capacidade da tabela.
     */
    private fun resize(newCapacity: Int) {
        val oldBuckets = buckets
        capacity = newCapacity
        buckets = arrayOfNulls(capacity)
        size = 0
        for (bucket in oldBuckets) {
            if (bucket != null) {
                for (entry in bucket) {
                    put(entry.key, entry.value)
                }
            }
        }
    }

    /**
     * Retorna representação textual da tabela no formato `{chave=valor, ...}`.
     *
     * @return string formatada com os pares chave-valor.
     */
    public override fun entries(): List<Pair<K, V>> =
        buckets.filterNotNull().flatten().map { it.key to it.value }

    public override fun iterator(): Iterator<Pair<K, V>> = entries().iterator()

    public override fun toString(): String {
        val entries = buckets.filterNotNull().flatten()
        return entries.joinToString(prefix = "{", postfix = "}") { "${it.key}=${it.value}" }
    }
}
