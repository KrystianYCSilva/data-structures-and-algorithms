package br.uem.din.datastructures.array

/**
 * Matriz (Array Multidimensional 2D).
 *
 * Representação de uma grade retangular de elementos com [rows] linhas e [cols] colunas.
 * Armazena os dados em um array linear interno (row-major order) para melhor localidade
 * de cache em comparação com `Array<Array<T>>`.
 *
 * Complexidades:
 * - Acesso/escrita `[i, j]`: O(1)
 * - [transpose]: O(rows × cols)
 * - [getRow] / [getColumn]: O(cols) / O(rows)
 *
 * Uso tipico: representação de grafos (matriz de adjacência), algoritmos de multiplicação
 * de matrizes (Strassen), programação dinâmica tabulada (Floyd-Warshall, LCS).
 *
 * @param T o tipo dos elementos armazenados.
 * @param rows o número de linhas.
 * @param cols o número de colunas.
 * @param init função de inicialização que recebe (row, col) e retorna o valor inicial.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 4 (Strassen),
 *             Cap. 25 (Floyd-Warshall).
 */
class Matrix<T>(val rows: Int, val cols: Int, init: (row: Int, col: Int) -> T) : Iterable<T> {

    private val data: MutableList<T>

    init {
        require(rows > 0 && cols > 0) { "Matrix dimensions must be positive: ${rows}x${cols}" }
        data = MutableList(rows * cols) { idx ->
            val r = idx / cols
            val c = idx % cols
            init(r, c)
        }
    }

    /**
     * Retorna o elemento na posição ([row], [col]).
     *
     * Complexidade: O(1).
     *
     * @throws IndexOutOfBoundsException se os índices forem inválidos.
     */
    operator fun get(row: Int, col: Int): T {
        checkBounds(row, col)
        return data[row * cols + col]
    }

    /**
     * Define o elemento na posição ([row], [col]).
     *
     * Complexidade: O(1).
     *
     * @throws IndexOutOfBoundsException se os índices forem inválidos.
     */
    operator fun set(row: Int, col: Int, value: T) {
        checkBounds(row, col)
        data[row * cols + col] = value
    }

    /**
     * Retorna uma linha inteira como lista imutável.
     *
     * Complexidade: O(cols).
     *
     * @param row o índice da linha (0-based).
     * @return lista com os elementos da linha.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun getRow(row: Int): List<T> {
        if (row < 0 || row >= rows) throw IndexOutOfBoundsException("Row: $row, Rows: $rows")
        val start = row * cols
        return data.subList(start, start + cols).toList()
    }

    /**
     * Retorna uma coluna inteira como lista imutável.
     *
     * Complexidade: O(rows).
     *
     * @param col o índice da coluna (0-based).
     * @return lista com os elementos da coluna.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun getColumn(col: Int): List<T> {
        if (col < 0 || col >= cols) throw IndexOutOfBoundsException("Col: $col, Cols: $cols")
        return List(rows) { row -> data[row * cols + col] }
    }

    /**
     * Retorna a transposta desta matriz (linhas viram colunas e vice-versa).
     *
     * Complexidade: O(rows × cols).
     *
     * @return nova [Matrix] com dimensões [cols] × [rows].
     */
    fun transpose(): Matrix<T> {
        return Matrix(cols, rows) { r, c -> this[c, r] }
    }

    /**
     * Aplica uma função a cada elemento, retornando uma nova matriz com os resultados.
     *
     * Complexidade: O(rows × cols).
     *
     * @param transform função de transformação aplicada a cada elemento.
     * @return nova [Matrix] com os valores transformados.
     */
    fun <R> map(transform: (T) -> R): Matrix<R> {
        return Matrix(rows, cols) { r, c -> transform(this[r, c]) }
    }

    /**
     * Aplica uma função a cada elemento junto com suas coordenadas.
     *
     * Complexidade: O(rows × cols).
     */
    fun forEach(action: (row: Int, col: Int, value: T) -> Unit) {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                action(r, c, this[r, c])
            }
        }
    }

    /**
     * Retorna o número total de elementos na matriz.
     *
     * Complexidade: O(1).
     */
    val size: Int get() = rows * cols

    /**
     * Verifica se a matriz contém o elemento especificado.
     *
     * Complexidade: O(rows × cols).
     *
     * @param element o valor a ser procurado.
     * @return `true` se encontrado, `false` caso contrário.
     */
    fun contains(element: T): Boolean = data.contains(element)

    /**
     * Uma matriz nunca está vazia (dimensões devem ser > 0).
     *
     * Complexidade: O(1).
     *
     * @return sempre `false`.
     */
    fun isEmpty(): Boolean = false

    /**
     * Retorna todos os elementos em row-major order como [List] imutável.
     *
     * Complexidade: O(rows × cols).
     *
     * @return lista com todos os elementos.
     */
    fun toList(): List<T> = data.toList()

    /**
     * Retorna todos os elementos como lista de listas (cada sub-lista é uma linha).
     *
     * Complexidade: O(rows × cols).
     *
     * @return lista de listas representando as linhas.
     */
    fun toNestedList(): List<List<T>> = List(rows) { r -> getRow(r) }

    /**
     * Retorna um [Iterator] que percorre todos os elementos em row-major order.
     *
     * Complexidade: O(1) para criação; O(rows × cols) para travessia completa.
     *
     * @return iterador sobre os elementos.
     */
    override fun iterator(): Iterator<T> = data.iterator()

    override fun toString(): String {
        val sb = StringBuilder()
        for (r in 0 until rows) {
            sb.append(getRow(r).joinToString(prefix = "[", postfix = "]"))
            if (r < rows - 1) sb.appendLine()
        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix<*>) return false
        return rows == other.rows && cols == other.cols && data == other.data
    }

    override fun hashCode(): Int {
        var result = rows
        result = 31 * result + cols
        result = 31 * result + data.hashCode()
        return result
    }

    private fun checkBounds(row: Int, col: Int) {
        if (row < 0 || row >= rows || col < 0 || col >= cols)
            throw IndexOutOfBoundsException("[$row, $col] out of bounds for ${rows}x${cols} matrix")
    }

    companion object {
        /**
         * Cria uma matriz identidade numérica N×N (requer valores 0 e 1 do tipo).
         *
         * @param n a dimensão da matriz quadrada.
         * @param zero o valor "zero" do tipo.
         * @param one o valor "um" do tipo.
         * @return matriz identidade N×N.
         */
        fun <T> identity(n: Int, zero: T, one: T): Matrix<T> {
            return Matrix(n, n) { r, c -> if (r == c) one else zero }
        }

        /**
         * Cria uma matriz preenchida com um valor constante.
         *
         * @param rows número de linhas.
         * @param cols número de colunas.
         * @param value o valor de preenchimento.
         * @return matriz [rows]×[cols] preenchida com [value].
         */
        fun <T> fill(rows: Int, cols: Int, value: T): Matrix<T> {
            return Matrix(rows, cols) { _, _ -> value }
        }
    }
}
