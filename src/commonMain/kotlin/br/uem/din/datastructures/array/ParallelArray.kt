package br.uem.din.datastructures.array

/**
 * Arrays paralelos genéricos (Parallel Arrays / Structure of Arrays — SoA).
 *
 * Em vez de manter um array de objetos (Array of Structures — AoS), utiliza arrays
 * separados para cada "coluna" de dados. Essa organização melhora a localidade de cache
 * ao processar colunas específicas isoladamente, pois os dados de um mesmo campo ficam
 * contíguos na memória.
 *
 * Esta implementação genérica aceita N colunas nomeadas de tipo `Any?`, permitindo
 * uso flexível para qualquer esquema de dados tabulares.
 *
 * Complexidades:
 * - [addRow]: O(1) amortizado (com redimensionamento por duplicação)
 * - [get]: O(1)
 * - [getColumn]: O(n)
 * - [removeAt]: O(n)
 *
 * Exemplo de uso:
 * ```kotlin
 * val pa = ParallelArray("id", "name", "score")
 * pa.addRow(1, "Alice", 9.5)
 * pa.addRow(2, "Bob", 8.3)
 * val name = pa.get(0, "name") // "Alice"
 * ```
 *
 * @param columnNames os nomes das colunas. Devem ser únicos.
 * @throws IllegalArgumentException se nomes de colunas forem duplicados.
 *
 * Referência: Drepper, U. "What Every Programmer Should Know About Memory" (2007);
 *             padrão SoA amplamente discutido em otimização de cache para Data-Oriented Design.
 */
class ParallelArray(vararg columnNames: String) {

    private val columnIndex: Map<String, Int>
    private val columns: Array<MutableList<Any?>>

    /**
     * Número de registros (linhas) armazenados.
     */
    var size = 0
        private set

    /**
     * Número de colunas definidas.
     */
    val columnCount: Int
        get() = columns.size

    /**
     * Nomes das colunas na ordem de definição.
     */
    val columnNames: List<String>

    init {
        require(columnNames.toSet().size == columnNames.size) {
            "Column names must be unique"
        }
        this.columnNames = columnNames.toList()
        columnIndex = columnNames.withIndex().associate { (i, name) -> name to i }
        columns = Array(columnNames.size) { mutableListOf<Any?>() }
    }

    /**
     * Adiciona uma nova linha de dados.
     *
     * O número de valores deve corresponder exatamente ao número de colunas.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param values os valores da nova linha, na mesma ordem das colunas.
     * @throws IllegalArgumentException se o número de valores não corresponder ao de colunas.
     */
    fun addRow(vararg values: Any?) {
        require(values.size == columns.size) {
            "Expected ${columns.size} values, got ${values.size}"
        }
        for (i in columns.indices) {
            columns[i].add(values[i])
        }
        size++
    }

    /**
     * Retorna o valor na posição [row], coluna [column].
     *
     * Complexidade: O(1).
     *
     * @param row o índice da linha (0-based).
     * @param column o nome da coluna.
     * @return o valor armazenado na célula.
     * @throws IndexOutOfBoundsException se o índice da linha for inválido.
     * @throws IllegalArgumentException se a coluna não existir.
     */
    fun get(row: Int, column: String): Any? {
        checkRow(row)
        val colIdx = requireColumn(column)
        return columns[colIdx][row]
    }

    /**
     * Retorna o valor na posição [row], coluna de índice [colIndex].
     *
     * Complexidade: O(1).
     *
     * @param row o índice da linha (0-based).
     * @param colIndex o índice da coluna (0-based).
     * @return o valor armazenado na célula.
     * @throws IndexOutOfBoundsException se os índices forem inválidos.
     */
    fun get(row: Int, colIndex: Int): Any? {
        checkRow(row)
        if (colIndex < 0 || colIndex >= columns.size)
            throw IndexOutOfBoundsException("Column index: $colIndex, Columns: ${columns.size}")
        return columns[colIndex][row]
    }

    /**
     * Retorna todos os valores de uma coluna como lista imutável.
     *
     * Complexidade: O(n) para a cópia.
     *
     * @param column o nome da coluna.
     * @return lista com todos os valores da coluna.
     * @throws IllegalArgumentException se a coluna não existir.
     */
    fun getColumn(column: String): List<Any?> {
        val colIdx = requireColumn(column)
        return columns[colIdx].toList()
    }

    /**
     * Retorna todos os valores de uma linha como lista imutável.
     *
     * Complexidade: O(k) onde k é o número de colunas.
     *
     * @param row o índice da linha (0-based).
     * @return lista com todos os valores da linha, na ordem das colunas.
     * @throws IndexOutOfBoundsException se o índice da linha for inválido.
     */
    fun getRow(row: Int): List<Any?> {
        checkRow(row)
        return columns.map { it[row] }
    }

    /**
     * Define o valor na posição [row], coluna [column].
     *
     * Complexidade: O(1).
     *
     * @param row o índice da linha (0-based).
     * @param column o nome da coluna.
     * @param value o novo valor.
     * @throws IndexOutOfBoundsException se o índice da linha for inválido.
     * @throws IllegalArgumentException se a coluna não existir.
     */
    fun set(row: Int, column: String, value: Any?) {
        checkRow(row)
        val colIdx = requireColumn(column)
        columns[colIdx][row] = value
    }

    /**
     * Remove a linha no índice especificado.
     *
     * Complexidade: O(n * k) onde n é o número de linhas restantes e k é o número de colunas.
     *
     * @param row o índice da linha a ser removida (0-based).
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun removeAt(row: Int) {
        checkRow(row)
        for (col in columns) {
            col.removeAt(row)
        }
        size--
    }

    /**
     * Retorna `true` se não houver linhas armazenadas.
     *
     * Complexidade: O(1).
     *
     * @return `true` se o array não contiver linhas.
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * Remove todas as linhas do array paralelo.
     *
     * Complexidade: O(k) onde k é o número de colunas.
     */
    fun clear() {
        for (col in columns) {
            col.clear()
        }
        size = 0
    }

    /**
     * Verifica se alguma célula contém o valor especificado.
     *
     * Complexidade: O(n × k) onde n é o número de linhas e k é o número de colunas.
     *
     * @param value o valor a ser procurado.
     * @return `true` se o valor existir em alguma célula.
     */
    fun contains(value: Any?): Boolean {
        for (col in columns) {
            if (col.contains(value)) return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ParallelArray) return false
        if (size != other.size) return false
        if (columnNames != other.columnNames) return false
        for (i in columns.indices) {
            if (columns[i] != other.columns[i]) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = columnNames.hashCode()
        result = 31 * result + size
        for (col in columns) {
            result = 31 * result + col.hashCode()
        }
        return result
    }

    override fun toString(): String {
        if (isEmpty()) return "ParallelArray(columns=${columnNames}, rows=0)"
        val sb = StringBuilder()
        sb.appendLine("ParallelArray(columns=${columnNames}, rows=$size)")
        for (r in 0 until size) {
            sb.append("  [$r] ")
            sb.appendLine(columns.mapIndexed { i, col -> "${columnNames[i]}=${col[r]}" }.joinToString())
        }
        return sb.toString()
    }

    private fun checkRow(row: Int) {
        if (row < 0 || row >= size)
            throw IndexOutOfBoundsException("Row index: $row, Size: $size")
    }

    private fun requireColumn(column: String): Int {
        return columnIndex[column]
            ?: throw IllegalArgumentException("Unknown column: '$column'. Available: $columnNames")
    }
}
