package br.uem.din.datastructures.hash

/**
 * Tabela hash com cuckoo hashing (Cuckoo Hash Table).
 *
 * Utiliza duas tabelas internas e duas funções de hash independentes para garantir
 * busca em tempo O(1) no pior caso. Na inserção, se a posição na primeira tabela
 * estiver ocupada, o elemento existente é "expulso" (como um cuco expulsa ovos do ninho)
 * e reinserido na tabela alternativa, podendo gerar uma cadeia de realocações.
 *
 * Se a cadeia de realocações exceder um limite (proporcional a log(n)), a tabela é
 * redimensionada (rehash) com novas funções de hash para eliminar ciclos.
 *
 * Complexidades:
 * - [get]: O(1) pior caso
 * - [contains]: O(1) pior caso
 * - [put]: O(1) amortizado (O(n) no pior caso durante rehash)
 * - [remove]: O(1) pior caso
 * - Espaço: O(n)
 *
 * @param K o tipo das chaves.
 * @param V o tipo dos valores.
 * @param initialCapacity capacidade inicial de cada tabela interna (padrão: 16).
 *
 * Referência: Pagh, R. & Rodler, F. F. "Cuckoo Hashing" (2004),
 *             Journal of Algorithms, 51(2), pp. 122–144.
 */
public class CuckooHashTable<K : Any, V>(initialCapacity: Int = 16) : MutableOpenHashTable<K, V> {

    private data class Entry<K, V>(val key: K, var value: V)

    private var capacity: Int = initialCapacity.coerceAtLeast(4)
    private var table1: Array<Entry<K, V>?> = arrayOfNulls(capacity)
    private var table2: Array<Entry<K, V>?> = arrayOfNulls(capacity)
    private var hashSeed1: Int = 0
    private var hashSeed2: Int = 1

    /**
     * Número de pares chave-valor armazenados na tabela.
     */
    public override var size: Int = 0
        private set

    /**
     * Limite máximo de realocações antes de acionar rehash.
     * Proporcional a log₂(capacidade) para evitar ciclos infinitos.
     */
    private val maxDisplacements: Int
        get() {
            var log = 0
            var n = capacity
            while (n > 1) { n /= 2; log++ }
            return (log * 6).coerceAtLeast(16)
        }

    /**
     * Insere ou atualiza o par chave-valor na tabela.
     *
     * Se a chave já existir em qualquer uma das duas tabelas, o valor é atualizado.
     * Caso contrário, tenta inserir na tabela 1. Se a posição estiver ocupada, inicia
     * uma cadeia de realocações entre as duas tabelas. Se o número de realocações
     * exceder [maxDisplacements], realiza rehash com novas funções de hash.
     *
     * Complexidade amortizada: O(1).
     *
     * @param key a chave a ser inserida ou atualizada.
     * @param value o valor associado à chave.
     */
    public override fun put(key: K, value: V) {
        val idx1 = hash1(key)
        val entry1 = table1[idx1]
        if (entry1 != null && entry1.key == key) {
            entry1.value = value
            return
        }

        val idx2 = hash2(key)
        val entry2 = table2[idx2]
        if (entry2 != null && entry2.key == key) {
            entry2.value = value
            return
        }

        insertEntry(Entry(key, value))
    }

    /**
     * Recupera o valor associado à chave especificada.
     *
     * Verifica ambas as tabelas nas posições determinadas pelas duas funções de hash.
     *
     * Complexidade: O(1) pior caso.
     *
     * @param key a chave a ser procurada.
     * @return o valor associado à chave, ou `null` se a chave não existir.
     */
    public override fun get(key: K): V? {
        val entry1 = table1[hash1(key)]
        if (entry1 != null && entry1.key == key) return entry1.value

        val entry2 = table2[hash2(key)]
        if (entry2 != null && entry2.key == key) return entry2.value

        return null
    }

    /**
     * Remove o par chave-valor associado à chave especificada.
     *
     * Complexidade: O(1) pior caso.
     *
     * @param key a chave a ser removida.
     * @return o valor removido, ou `null` se a chave não existir.
     */
    public override fun remove(key: K): V? {
        val idx1 = hash1(key)
        val entry1 = table1[idx1]
        if (entry1 != null && entry1.key == key) {
            table1[idx1] = null
            size--
            return entry1.value
        }

        val idx2 = hash2(key)
        val entry2 = table2[idx2]
        if (entry2 != null && entry2.key == key) {
            table2[idx2] = null
            size--
            return entry2.value
        }

        return null
    }

    /**
     * Verifica se a tabela contém a chave especificada.
     *
     * Complexidade: O(1) pior caso.
     *
     * @param key a chave a ser verificada.
     * @return `true` se a chave existir na tabela, `false` caso contrário.
     */
    public override fun contains(key: K): Boolean {
        val entry1 = table1[hash1(key)]
        if (entry1 != null && entry1.key == key) return true

        val entry2 = table2[hash2(key)]
        return entry2 != null && entry2.key == key
    }

    /**
     * Realiza a inserção efetiva de uma entrada, executando a cadeia de realocações
     * entre as duas tabelas conforme necessário.
     *
     * @param entry a entrada a ser inserida.
     */
    private fun insertEntry(entry: Entry<K, V>) {
        var current = entry
        for (i in 0 until maxDisplacements) {
            val idx1 = hash1(current.key)
            val existing1 = table1[idx1]
            table1[idx1] = current
            if (existing1 == null) {
                size++
                return
            }
            current = existing1

            val idx2 = hash2(current.key)
            val existing2 = table2[idx2]
            table2[idx2] = current
            if (existing2 == null) {
                size++
                return
            }
            current = existing2
        }

        rehash()
        insertEntry(current)
    }

    /**
     * Redimensiona ambas as tabelas (dobra a capacidade) e reinsere todos os elementos
     * com novas sementes de hash para eliminar ciclos de realocação.
     */
    private fun rehash() {
        val allEntries = collectAllEntries()
        capacity *= 2
        hashSeed1 += 2
        hashSeed2 += 2
        table1 = arrayOfNulls(capacity)
        table2 = arrayOfNulls(capacity)
        size = 0
        for (e in allEntries) {
            insertEntry(e)
        }
    }

    /**
     * Coleta todas as entradas válidas de ambas as tabelas.
     *
     * @return lista de todas as entradas armazenadas.
     */
    private fun collectAllEntries(): List<Entry<K, V>> {
        val entries = mutableListOf<Entry<K, V>>()
        for (e in table1) { if (e != null) entries.add(e) }
        for (e in table2) { if (e != null) entries.add(e) }
        return entries
    }

    /**
     * Primeira função de hash. Combina o hashCode da chave com [hashSeed1]
     * utilizando multiplicação por constante prima (método multiplicativo).
     *
     * @param key a chave a ser hasheada.
     * @return índice na primeira tabela.
     */
    private fun hash1(key: K): Int {
        val h = key.hashCode() xor (hashSeed1 * 0x9E3779B9.toInt())
        return (h and 0x7FFFFFFF) % capacity
    }

    /**
     * Segunda função de hash, independente da primeira. Utiliza [hashSeed2]
     * com uma constante prima diferente para dispersão.
     *
     * @param key a chave a ser hasheada.
     * @return índice na segunda tabela.
     */
    private fun hash2(key: K): Int {
        val h = key.hashCode() xor (hashSeed2 * 0x517CC1B7.toInt())
        return (h and 0x7FFFFFFF) % capacity
    }

    /**
     * Retorna representação textual da tabela no formato `{chave=valor, ...}`.
     *
     * @return string formatada com os pares chave-valor.
     */
    public override fun entries(): List<Pair<K, V>> =
        collectAllEntries().map { it.key to it.value }

    public override fun iterator(): Iterator<Pair<K, V>> = entries().iterator()

    public override fun toString(): String {
        val entries = collectAllEntries()
        return entries.joinToString(prefix = "{", postfix = "}") { "${it.key}=${it.value}" }
    }
}

