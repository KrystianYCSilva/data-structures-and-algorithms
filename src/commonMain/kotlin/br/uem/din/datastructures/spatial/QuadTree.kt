package br.uem.din.datastructures.spatial

/**
 * Quadtree — estrutura de dados para particionamento espacial em duas dimensões.
 *
 * Uma Quadtree divide recursivamente o espaço 2D em quatro quadrantes (NW, NE, SW, SE).
 * Cada nó interno representa uma região retangular e possui exatamente quatro filhos,
 * um para cada quadrante. Pontos são armazenados nas folhas.
 *
 * Quando uma folha excede a capacidade máxima [nodeCapacity], ela é subdividida
 * em quatro filhos. A profundidade máxima [maxDepth] limita a subdivisão para
 * evitar recursão infinita com pontos coincidentes.
 *
 * Esta implementação utiliza a variante "point quadtree" com bucketing,
 * adequada para conjuntos de pontos distribuídos irregularmente.
 *
 * Complexidades (para distribuição uniforme):
 * - [insert]: O(log n) esperado, O(n) pior caso (pontos coincidentes)
 * - [query]: O(log n + k), onde k é o número de pontos retornados
 * - [contains]: O(log n) esperado
 * - Espaço: O(n)
 *
 * @param boundary o retângulo que define os limites do espaço gerenciado por esta quadtree.
 * @param nodeCapacity número máximo de pontos por folha antes de subdividir (padrão: 4).
 * @param maxDepth profundidade máxima da árvore (padrão: 20).
 *
 * Referência: Finkel, R. A. & Bentley, J. L. "Quad Trees: A Data Structure for Retrieval
 *             on Composite Keys" (1974);
 *             Samet, H. "The Design and Analysis of Spatial Data Structures" (1990).
 */
class QuadTree(
    private val boundary: Rectangle,
    private val nodeCapacity: Int = 4,
    private val maxDepth: Int = 20
) {

    /**
     * Ponto em espaço bidimensional.
     *
     * @property x a coordenada horizontal.
     * @property y a coordenada vertical.
     */
    data class Point(val x: Double, val y: Double)

    /**
     * Retângulo alinhado aos eixos (axis-aligned bounding box — AABB).
     *
     * Definido pelo centro e metade da largura/altura (half-dimensions),
     * o que simplifica os cálculos de contenção e interseção.
     *
     * @property cx a coordenada x do centro.
     * @property cy a coordenada y do centro.
     * @property halfWidth metade da largura do retângulo.
     * @property halfHeight metade da altura do retângulo.
     */
    data class Rectangle(
        val cx: Double,
        val cy: Double,
        val halfWidth: Double,
        val halfHeight: Double
    ) {
        /**
         * Verifica se um ponto está contido neste retângulo (inclusive nas bordas).
         *
         * Complexidade: O(1).
         *
         * @param point o ponto a ser testado.
         * @return `true` se o ponto estiver dentro ou na borda do retângulo.
         */
        fun containsPoint(point: Point): Boolean =
            point.x >= cx - halfWidth && point.x <= cx + halfWidth &&
                    point.y >= cy - halfHeight && point.y <= cy + halfHeight

        /**
         * Verifica se este retângulo intersecta outro retângulo.
         *
         * Dois AABBs se intersectam se e somente se se sobrepõem em ambos os eixos.
         *
         * Complexidade: O(1).
         *
         * @param other o outro retângulo.
         * @return `true` se houver interseção.
         */
        fun intersects(other: Rectangle): Boolean =
            !(other.cx - other.halfWidth > cx + halfWidth ||
                    other.cx + other.halfWidth < cx - halfWidth ||
                    other.cy - other.halfHeight > cy + halfHeight ||
                    other.cy + other.halfHeight < cy - halfHeight)
    }

    private val points: MutableList<Point> = mutableListOf()
    private var northWest: QuadTree? = null
    private var northEast: QuadTree? = null
    private var southWest: QuadTree? = null
    private var southEast: QuadTree? = null
    private var divided: Boolean = false
    private var depth: Int = 0

    /** Número total de pontos armazenados nesta quadtree e suas subárvores. */
    var size: Int = 0
        private set

    /**
     * Insere um ponto na quadtree.
     *
     * Se o ponto estiver fora dos limites da quadtree, a inserção é ignorada.
     * Se a folha atual estiver cheia e a profundidade máxima não foi atingida,
     * a folha é subdividida e os pontos são redistribuídos.
     *
     * Complexidade: O(log n) esperado para distribuição uniforme.
     *
     * @param point o ponto a ser inserido.
     * @return `true` se o ponto foi inserido com sucesso, `false` se estiver fora dos limites.
     */
    fun insert(point: Point): Boolean {
        if (!boundary.containsPoint(point)) return false

        if (!divided && (points.size < nodeCapacity || depth >= maxDepth)) {
            points.add(point)
            size++
            return true
        }

        if (!divided) {
            subdivide()
        }

        if (northWest!!.insert(point)) { size++; return true }
        if (northEast!!.insert(point)) { size++; return true }
        if (southWest!!.insert(point)) { size++; return true }
        if (southEast!!.insert(point)) { size++; return true }

        return false
    }

    /**
     * Busca todos os pontos contidos dentro de uma região retangular (range search).
     *
     * Percorre a árvore podando subárvores cujos limites não intersectam a região de busca.
     *
     * Complexidade: O(log n + k), onde k é o número de pontos encontrados.
     *
     * @param range o retângulo que define a região de busca.
     * @return uma lista de todos os pontos encontrados dentro da região.
     */
    fun query(range: Rectangle): List<Point> {
        val found = mutableListOf<Point>()
        query(range, found)
        return found
    }

    /**
     * Verifica se a quadtree contém um ponto específico.
     *
     * Complexidade: O(log n) esperado para distribuição uniforme.
     *
     * @param point o ponto a ser procurado.
     * @return `true` se o ponto existir na quadtree.
     */
    fun contains(point: Point): Boolean {
        if (!boundary.containsPoint(point)) return false

        for (p in points) {
            if (p.x == point.x && p.y == point.y) return true
        }

        if (divided) {
            return northWest!!.contains(point) ||
                    northEast!!.contains(point) ||
                    southWest!!.contains(point) ||
                    southEast!!.contains(point)
        }

        return false
    }

    /**
     * Retorna todos os pontos armazenados na quadtree.
     *
     * Complexidade: O(n).
     *
     * @return uma lista de todos os pontos.
     */
    fun allPoints(): List<Point> {
        val result = mutableListOf<Point>()
        collectAll(result)
        return result
    }

    /**
     * Implementação recursiva interna da busca por intervalo.
     *
     * @param range a região de busca.
     * @param found a lista acumuladora de pontos encontrados.
     */
    private fun query(range: Rectangle, found: MutableList<Point>) {
        if (!boundary.intersects(range)) return

        for (p in points) {
            if (range.containsPoint(p)) {
                found.add(p)
            }
        }

        if (divided) {
            northWest!!.query(range, found)
            northEast!!.query(range, found)
            southWest!!.query(range, found)
            southEast!!.query(range, found)
        }
    }

    /**
     * Coleta todos os pontos da árvore recursivamente.
     *
     * @param result a lista acumuladora.
     */
    private fun collectAll(result: MutableList<Point>) {
        result.addAll(points)
        if (divided) {
            northWest!!.collectAll(result)
            northEast!!.collectAll(result)
            southWest!!.collectAll(result)
            southEast!!.collectAll(result)
        }
    }

    /**
     * Subdivide esta folha em quatro quadrantes.
     *
     * Cria quatro filhos com limites correspondentes a NW, NE, SW e SE.
     * Os pontos existentes na folha são redistribuídos para os filhos apropriados.
     *
     * Complexidade: O(nodeCapacity).
     */
    private fun subdivide() {
        val cx = boundary.cx
        val cy = boundary.cy
        val hw = boundary.halfWidth / 2.0
        val hh = boundary.halfHeight / 2.0
        val childDepth = depth + 1

        northWest = QuadTree(Rectangle(cx - hw, cy + hh, hw, hh), nodeCapacity, maxDepth)
            .also { it.depth = childDepth }
        northEast = QuadTree(Rectangle(cx + hw, cy + hh, hw, hh), nodeCapacity, maxDepth)
            .also { it.depth = childDepth }
        southWest = QuadTree(Rectangle(cx - hw, cy - hh, hw, hh), nodeCapacity, maxDepth)
            .also { it.depth = childDepth }
        southEast = QuadTree(Rectangle(cx + hw, cy - hh, hw, hh), nodeCapacity, maxDepth)
            .also { it.depth = childDepth }

        divided = true

        val existingPoints = points.toList()
        points.clear()
        size = 0

        for (p in existingPoints) {
            insert(p)
        }
    }
}
