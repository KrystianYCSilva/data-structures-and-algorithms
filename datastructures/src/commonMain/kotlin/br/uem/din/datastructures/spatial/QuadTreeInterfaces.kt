package br.uem.din.datastructures.spatial

/**
 * Interface somente-leitura para uma Quadtree.
 *
 * Define operações de consulta: busca por ponto, busca por região e listagem.
 *
 * Referência: Finkel, R. A. & Bentley, J. L. "Quad Trees" (1974).
 *
 * @see MutableQuadTree
 */
public interface ImmutableQuadTree {
    /**
     * Número total de pontos armazenados.
     */
    public val size: Int

    /**
     * Verifica se a quadtree contém um ponto específico.
     *
     * @param point o ponto a ser procurado.
     * @return `true` se o ponto existir na quadtree.
     */
    public fun contains(point: QuadTree.Point): Boolean

    /**
     * Busca todos os pontos contidos dentro de uma região retangular.
     *
     * @param range o retângulo que define a região de busca.
     * @return lista de pontos encontrados dentro da região.
     */
    public fun query(range: QuadTree.Rectangle): List<QuadTree.Point>

    /**
     * Retorna todos os pontos armazenados na quadtree.
     *
     * @return lista de todos os pontos.
     */
    public fun allPoints(): List<QuadTree.Point>
}

/**
 * Interface mutável para uma Quadtree.
 *
 * Estende [ImmutableQuadTree] adicionando a operação de inserção.
 *
 * Referência: Finkel, R. A. & Bentley, J. L. "Quad Trees" (1974).
 *
 * @see ImmutableQuadTree
 */
public interface MutableQuadTree : ImmutableQuadTree {
    /**
     * Insere um ponto na quadtree.
     *
     * @param point o ponto a ser inserido.
     * @return `true` se o ponto foi inserido com sucesso.
     */
    public fun insert(point: QuadTree.Point): Boolean
}
