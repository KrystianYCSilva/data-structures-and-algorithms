package br.uem.din.datastructures.array

/**
 * Interface somente-leitura para Arrays Paralelos (Structure of Arrays).
 *
 * Define operações de consulta: acesso por célula, linhas, colunas e metadados.
 *
 * Referência: Drepper, U. "What Every Programmer Should Know About Memory" (2007).
 *
 * @see MutableParallelArray
 */
public interface ImmutableParallelArray {
    /**
     * Número de registros (linhas) armazenados.
     */
    public val size: Int

    /**
     * Número de colunas definidas.
     */
    public val columnCount: Int

    /**
     * Nomes das colunas na ordem de definição.
     */
    public val columnNames: List<String>

    /**
     * Retorna o valor na posição [row], coluna [column].
     */
    public fun get(row: Int, column: String): Any?

    /**
     * Retorna o valor na posição [row], coluna de índice [colIndex].
     */
    public fun get(row: Int, colIndex: Int): Any?

    /**
     * Retorna todos os valores de uma coluna como lista imutável.
     */
    public fun getColumn(column: String): List<Any?>

    /**
     * Retorna todos os valores de uma linha como lista imutável.
     */
    public fun getRow(row: Int): List<Any?>

    /**
     * Retorna `true` se não houver linhas armazenadas.
     */
    public fun isEmpty(): Boolean

    /**
     * Verifica se alguma célula contém o valor especificado.
     */
    public fun contains(value: Any?): Boolean
}

/**
 * Interface mutável para Arrays Paralelos (Structure of Arrays).
 *
 * Estende [ImmutableParallelArray] adicionando operações de modificação.
 *
 * @see ImmutableParallelArray
 */
public interface MutableParallelArray : ImmutableParallelArray {
    /**
     * Adiciona uma nova linha de dados.
     */
    public fun addRow(vararg values: Any?)

    /**
     * Define o valor na posição [row], coluna [column].
     */
    public fun set(row: Int, column: String, value: Any?)

    /**
     * Remove a linha no índice especificado.
     */
    public fun removeAt(row: Int)

    /**
     * Remove todas as linhas.
     */
    public fun clear()
}
