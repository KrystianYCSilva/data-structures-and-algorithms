package br.uem.din.datastructures.array

/**
 * Interface somente-leitura para uma Matriz 2D.
 *
 * Define operações de consulta: acesso por coordenada, linhas, colunas e iteração.
 *
 * @param T o tipo dos elementos armazenados.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 4 (Strassen).
 *
 * @see MutableMatrix
 */
public interface ImmutableMatrix<T> : Iterable<T> {
    /**
     * Número de linhas.
     */
    public val rows: Int

    /**
     * Número de colunas.
     */
    public val cols: Int

    /**
     * Número total de elementos.
     */
    public val size: Int

    /**
     * Retorna o elemento na posição ([row], [col]).
     */
    public operator fun get(row: Int, col: Int): T

    /**
     * Retorna uma linha inteira como lista imutável.
     */
    public fun getRow(row: Int): List<T>

    /**
     * Retorna uma coluna inteira como lista imutável.
     */
    public fun getColumn(col: Int): List<T>

    /**
     * Verifica se a matriz contém o elemento especificado.
     */
    public fun contains(element: T): Boolean

    /**
     * Retorna todos os elementos em row-major order.
     */
    public fun toList(): List<T>

    /**
     * Retorna a transposta desta matriz.
     */
    public fun transpose(): Matrix<T>
}

/**
 * Interface mutável para uma Matriz 2D.
 *
 * Estende [ImmutableMatrix] adicionando operação de escrita.
 *
 * @param T o tipo dos elementos armazenados.
 *
 * @see ImmutableMatrix
 */
public interface MutableMatrix<T> : ImmutableMatrix<T> {
    /**
     * Define o elemento na posição ([row], [col]).
     */
    public operator fun set(row: Int, col: Int, value: T)
}
