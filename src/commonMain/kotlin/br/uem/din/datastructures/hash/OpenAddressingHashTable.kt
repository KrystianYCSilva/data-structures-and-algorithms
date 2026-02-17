package br.uem.din.datastructures.hash

/**
 * Tabela hash com endereçamento aberto (Open Addressing Hash Table).
 *
 * Resolve colisões por sondagem direta na própria tabela, sem encadeamento externo.
 * Suporta três estratégias de sondagem: linear, quadrática e hash duplo.
 *
 * A tabela redimensiona automaticamente (rehash) quando o fator de carga excede [maxLoadFactor].
 * Remoções utilizam marcadores "tombstone" (lápide) para preservar a integridade das
 * sequências de sondagem, conforme descrito em Cormen Cap. 11.4.
 *
 * Complexidades (esperadas, assumindo hash uniforme simples):
 * - [put]: O(1/(1 - α)) amortizado, onde α é o fator de carga
 * - [get]: O(1/(1 - α))
 * - [remove]: O(1/(1 - α))
 * - [contains]: O(1/(1 - α))
 * - Espaço: O(n)
 *
 * @param K o tipo das chaves.
 * @param V o tipo dos valores.
 * @param initialCapacity capacidade inicial da tabela (padrão: 16).
 * @param maxLoadFactor fator de carga máximo antes do rehash (padrão: 0.7).
 * @param probingStrategy estratégia de sondagem a ser utilizada (padrão: [ProbingStrategy.LINEAR]).
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 11 — Hash Tables (Open Addressing).
 */
public class OpenAddressingHashTable<K : Any, V>(
    initialCapacity: Int = 16,
    private val maxLoadFactor: Double = 0.7,
    private val probingStrategy: ProbingStrategy = ProbingStrategy.LINEAR
) : MutableOpenHashTable<K, V> {

    /**
     * Estratégias de sondagem disponíveis para resolução de colisões.
     *
     * - [LINEAR]: h(k, i) = (h'(k) + i) mod m — sondagem linear com incremento constante.
     * - [QUADRATIC]: h(k, i) = (h'(k) + c₁·i + c₂·i²) mod m — sondagem quadrática.
     * - [DOUBLE_HASHING]: h(k, i) = (h₁(k) + i·h₂(k)) mod m — hash duplo com duas funções independentes.
     *
     * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 11.4.
     */
    public enum class ProbingStrategy {
        LINEAR,
        QUADRATIC,
        DOUBLE_HASHING
    }

    private sealed class Entry<K, V> {
        data class Occupied<K, V>(val key: K, var value: V) : Entry<K, V>()
        class Tombstone<K, V> : Entry<K, V>()
    }

    private var capacity: Int = initialCapacity.coerceAtLeast(4)
    private var table: Array<Entry<K, V>?> = arrayOfNulls(capacity)

    /**
     * Número de pares chave-valor armazenados na tabela.
     */
    public override var size: Int = 0
        private set

    /**
     * Insere ou atualiza o par chave-valor na tabela.
     *
     * Se a chave já existir, o valor é atualizado. Caso contrário, um novo par é inserido.
     * Realiza rehash se o fator de carga exceder [maxLoadFactor] após a inserção.
     *
     * Complexidade amortizada: O(1/(1 - α)).
     *
     * @param key a chave a ser inserida ou atualizada.
     * @param value o valor associado à chave.
     */
    public override fun put(key: K, value: V) {
        if ((size + 1).toDouble() / capacity > maxLoadFactor) {
            resize(capacity * 2)
        }
        var firstTombstone = -1
        for (i in 0 until capacity) {
            val index = probe(key, i)
            when (val entry = table[index]) {
                null -> {
                    val insertIndex = if (firstTombstone != -1) firstTombstone else index
                    table[insertIndex] = Entry.Occupied(key, value)
                    size++
                    return
                }
                is Entry.Tombstone -> {
                    if (firstTombstone == -1) firstTombstone = index
                }
                is Entry.Occupied -> {
                    if (entry.key == key) {
                        entry.value = value
                        return
                    }
                }
            }
        }
        if (firstTombstone != -1) {
            table[firstTombstone] = Entry.Occupied(key, value)
            size++
        }
    }

    /**
     * Recupera o valor associado à chave especificada.
     *
     * Complexidade esperada: O(1/(1 - α)).
     *
     * @param key a chave a ser procurada.
     * @return o valor associado à chave, ou `null` se a chave não existir.
     */
    public override fun get(key: K): V? {
        for (i in 0 until capacity) {
            val index = probe(key, i)
            when (val entry = table[index]) {
                null -> return null
                is Entry.Tombstone -> continue
                is Entry.Occupied -> {
                    if (entry.key == key) return entry.value
                }
            }
        }
        return null
    }

    /**
     * Remove o par chave-valor associado à chave especificada.
     *
     * A posição removida é marcada com um tombstone para preservar a integridade
     * das sequências de sondagem subsequentes.
     *
     * Complexidade esperada: O(1/(1 - α)).
     *
     * @param key a chave a ser removida.
     * @return o valor removido, ou `null` se a chave não existir.
     */
    public override fun remove(key: K): V? {
        for (i in 0 until capacity) {
            val index = probe(key, i)
            when (val entry = table[index]) {
                null -> return null
                is Entry.Tombstone -> continue
                is Entry.Occupied -> {
                    if (entry.key == key) {
                        table[index] = Entry.Tombstone()
                        size--
                        return entry.value
                    }
                }
            }
        }
        return null
    }

    /**
     * Verifica se a tabela contém a chave especificada.
     *
     * Complexidade esperada: O(1/(1 - α)).
     *
     * @param key a chave a ser verificada.
     * @return `true` se a chave existir na tabela, `false` caso contrário.
     */
    public override fun contains(key: K): Boolean = get(key) != null

    /**
     * Calcula o índice de sondagem para a iteração `i` da chave `key`,
     * conforme a [probingStrategy] configurada.
     *
     * @param key a chave sendo sondada.
     * @param i o número da tentativa de sondagem (0, 1, 2, ...).
     * @return o índice na tabela para esta tentativa.
     */
    private fun probe(key: K, i: Int): Int {
        val h = key.hashCode() and 0x7FFFFFFF
        return when (probingStrategy) {
            ProbingStrategy.LINEAR -> (h + i) % capacity
            ProbingStrategy.QUADRATIC -> (h + i + i * i) % capacity
            ProbingStrategy.DOUBLE_HASHING -> {
                val h2 = 1 + (h % (capacity - 1))
                (h + i * h2) % capacity
            }
        }
    }

    /**
     * Redimensiona a tabela para a nova capacidade, reinserindo todos os elementos ocupados.
     *
     * Tombstones são descartados durante o processo de rehash.
     *
     * @param newCapacity a nova capacidade da tabela.
     */
    private fun resize(newCapacity: Int) {
        val oldTable = table
        capacity = newCapacity
        table = arrayOfNulls(capacity)
        size = 0
        for (entry in oldTable) {
            if (entry is Entry.Occupied) {
                put(entry.key, entry.value)
            }
        }
    }

    /**
     * Retorna representação textual da tabela no formato `{chave=valor, ...}`.
     *
     * @return string formatada com os pares chave-valor.
     */
    public override fun toString(): String {
        val entries = table.filterIsInstance<Entry.Occupied<K, V>>()
        return entries.joinToString(prefix = "{", postfix = "}") { "${it.key}=${it.value}" }
    }
}
