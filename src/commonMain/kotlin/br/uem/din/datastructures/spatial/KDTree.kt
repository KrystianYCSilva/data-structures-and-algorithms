package br.uem.din.datastructures.spatial

import kotlin.math.sqrt

/**
 * KD-Tree (k-dimensional tree) — árvore binária de busca para particionamento espacial em k dimensões.
 *
 * Uma KD-Tree é uma BST generalizada onde cada nível da árvore alterna a dimensão
 * usada como critério de particionamento. No nível d, a dimensão de corte é d mod k.
 * Todos os pontos com coordenada menor na dimensão de corte ficam na subárvore esquerda,
 * e os demais na subárvore direita.
 *
 * Esta implementação suporta dimensão arbitrária k, definida pelo tamanho dos arrays
 * de coordenadas dos pontos inseridos. A construção é incremental (inserção ponto a ponto).
 *
 * Complexidades (para árvore balanceada, n pontos em k dimensões):
 * - [insert]: O(log n) esperado, O(n) pior caso (degenerado)
 * - [nearestNeighbor]: O(log n) esperado, O(n) pior caso
 * - [rangeSearch]: O(n^{1-1/k} + m), onde m é o número de pontos retornados
 * - [contains]: O(log n) esperado
 * - Espaço: O(n)
 *
 * @param k o número de dimensões do espaço.
 *
 * Referência: Bentley, J. L. "Multidimensional binary search trees used for associative
 *             searching" (1975);
 *             Cormen, T. H. et al. "Introduction to Algorithms" — problema 12-4 (variação);
 *             Friedman, J. H., Bentley, J. L. & Finkel, R. A. "An Algorithm for Finding
 *             Best Matches in Logarithmic Expected Time" (1977).
 */
class KDTree(private val k: Int) {

    init {
        require(k >= 1) { "O número de dimensões deve ser pelo menos 1." }
    }

    /**
     * Nó interno da KD-Tree.
     *
     * @property point as coordenadas do ponto armazenado (array de tamanho k).
     * @property left filho esquerdo (pontos com coordenada menor na dimensão de corte).
     * @property right filho direito (pontos com coordenada maior ou igual na dimensão de corte).
     */
    internal class Node(
        val point: DoubleArray,
        var left: Node? = null,
        var right: Node? = null
    )

    private var root: Node? = null

    /** Número de pontos armazenados na KD-Tree. */
    var size: Int = 0
        private set

    /**
     * Verifica se a árvore está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se a árvore não contiver pontos.
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * Insere um ponto na KD-Tree.
     *
     * O ponto é inserido percorrendo a árvore e alternando a dimensão de comparação
     * a cada nível. No nível d, compara-se a coordenada d mod k.
     *
     * Complexidade: O(log n) esperado, O(n) pior caso.
     *
     * @param point as coordenadas do ponto (array de tamanho [k]).
     * @throws IllegalArgumentException se o tamanho do array não for igual a [k].
     */
    fun insert(point: DoubleArray) {
        require(point.size == k) { "O ponto deve ter exatamente $k dimensões." }
        root = insert(root, point, 0)
        size++
    }

    /**
     * Verifica se a KD-Tree contém um ponto específico.
     *
     * Complexidade: O(log n) esperado.
     *
     * @param point as coordenadas do ponto a ser procurado.
     * @return `true` se o ponto existir na árvore.
     * @throws IllegalArgumentException se o tamanho do array não for igual a [k].
     */
    fun contains(point: DoubleArray): Boolean {
        require(point.size == k) { "O ponto deve ter exatamente $k dimensões." }
        return contains(root, point, 0)
    }

    /**
     * Encontra o vizinho mais próximo (nearest neighbor) de um ponto alvo.
     *
     * Utiliza o algoritmo de busca com poda (pruning): percorre a árvore para o lado
     * mais promissor, depois verifica se o outro lado pode conter um ponto mais próximo
     * comparando a distância ao hiperplano de corte com a melhor distância encontrada.
     *
     * Complexidade: O(log n) esperado para distribuições bem comportadas,
     *               O(n) pior caso para distribuições adversas.
     *
     * @param target as coordenadas do ponto alvo.
     * @return as coordenadas do vizinho mais próximo, ou `null` se a árvore estiver vazia.
     * @throws IllegalArgumentException se o tamanho do array não for igual a [k].
     */
    fun nearestNeighbor(target: DoubleArray): DoubleArray? {
        require(target.size == k) { "O ponto alvo deve ter exatamente $k dimensões." }
        if (root == null) return null
        val best = nearestNeighbor(root!!, target, 0, root!!.point, distanceSquared(target, root!!.point))
        return best
    }

    /**
     * Busca por intervalo ortogonal (range search).
     *
     * Retorna todos os pontos contidos dentro de um hiper-retângulo definido
     * por coordenadas mínimas e máximas em cada dimensão.
     *
     * Utiliza poda de subárvores cujos subespaços não intersectam a região de busca.
     *
     * Complexidade: O(n^{1-1/k} + m), onde m é o número de pontos retornados
     *               (resultado de Bentley & Friedman, 1979).
     *
     * @param lowerBound coordenadas mínimas do hiper-retângulo (array de tamanho [k]).
     * @param upperBound coordenadas máximas do hiper-retângulo (array de tamanho [k]).
     * @return lista de pontos dentro da região.
     * @throws IllegalArgumentException se os tamanhos dos arrays não forem iguais a [k].
     */
    fun rangeSearch(lowerBound: DoubleArray, upperBound: DoubleArray): List<DoubleArray> {
        require(lowerBound.size == k) { "O limite inferior deve ter exatamente $k dimensões." }
        require(upperBound.size == k) { "O limite superior deve ter exatamente $k dimensões." }
        val results = mutableListOf<DoubleArray>()
        rangeSearch(root, lowerBound, upperBound, 0, results)
        return results
    }

    /**
     * Inserção recursiva auxiliar.
     *
     * @param node o nó atual.
     * @param point o ponto a ser inserido.
     * @param depth a profundidade atual (determina a dimensão de corte).
     * @return o nó raiz da subárvore após a inserção.
     */
    private fun insert(node: Node?, point: DoubleArray, depth: Int): Node {
        if (node == null) return Node(point)
        val dim = depth % k
        if (point[dim] < node.point[dim]) {
            node.left = insert(node.left, point, depth + 1)
        } else {
            node.right = insert(node.right, point, depth + 1)
        }
        return node
    }

    /**
     * Busca recursiva auxiliar para verificação de existência.
     *
     * @param node o nó atual.
     * @param point o ponto procurado.
     * @param depth a profundidade atual.
     * @return `true` se o ponto for encontrado.
     */
    private fun contains(node: Node?, point: DoubleArray, depth: Int): Boolean {
        if (node == null) return false
        if (pointsEqual(node.point, point)) return true
        val dim = depth % k
        return if (point[dim] < node.point[dim]) {
            contains(node.left, point, depth + 1)
        } else {
            contains(node.right, point, depth + 1)
        }
    }

    /**
     * Busca recursiva do vizinho mais próximo com poda.
     *
     * Para cada nó, verifica se o ponto atual é mais próximo do alvo que o melhor
     * candidato encontrado até o momento. Em seguida, decide a ordem de visitação
     * dos filhos e poda o lado distante se a distância ao hiperplano de corte
     * for maior que a melhor distância atual.
     *
     * @param node o nó atual.
     * @param target o ponto alvo.
     * @param depth a profundidade atual.
     * @param bestPoint o melhor ponto encontrado até agora.
     * @param bestDistSq a distância euclidiana ao quadrado do melhor ponto.
     * @return as coordenadas do vizinho mais próximo encontrado nesta subárvore.
     */
    private fun nearestNeighbor(
        node: Node,
        target: DoubleArray,
        depth: Int,
        bestPoint: DoubleArray,
        bestDistSq: Double
    ): DoubleArray {
        var currentBest = bestPoint
        var currentBestDistSq = bestDistSq

        val dist = distanceSquared(target, node.point)
        if (dist < currentBestDistSq) {
            currentBest = node.point
            currentBestDistSq = dist
        }

        val dim = depth % k
        val diff = target[dim] - node.point[dim]
        val diffSq = diff * diff

        val nearChild = if (diff < 0) node.left else node.right
        val farChild = if (diff < 0) node.right else node.left

        if (nearChild != null) {
            val candidate = nearestNeighbor(nearChild, target, depth + 1, currentBest, currentBestDistSq)
            val candidateDist = distanceSquared(target, candidate)
            if (candidateDist < currentBestDistSq) {
                currentBest = candidate
                currentBestDistSq = candidateDist
            }
        }

        if (farChild != null && diffSq < currentBestDistSq) {
            val candidate = nearestNeighbor(farChild, target, depth + 1, currentBest, currentBestDistSq)
            val candidateDist = distanceSquared(target, candidate)
            if (candidateDist < currentBestDistSq) {
                currentBest = candidate
                currentBestDistSq = candidateDist
            }
        }

        return currentBest
    }

    /**
     * Busca por intervalo recursiva auxiliar com poda.
     *
     * Para cada nó, verifica se o ponto está dentro da região e, em seguida,
     * poda subárvores que não podem conter pontos dentro da região.
     *
     * @param node o nó atual.
     * @param lower coordenadas mínimas da região.
     * @param upper coordenadas máximas da região.
     * @param depth a profundidade atual.
     * @param results lista acumuladora de pontos encontrados.
     */
    private fun rangeSearch(
        node: Node?,
        lower: DoubleArray,
        upper: DoubleArray,
        depth: Int,
        results: MutableList<DoubleArray>
    ) {
        if (node == null) return

        if (isPointInRange(node.point, lower, upper)) {
            results.add(node.point)
        }

        val dim = depth % k

        if (lower[dim] <= node.point[dim]) {
            rangeSearch(node.left, lower, upper, depth + 1, results)
        }
        if (upper[dim] >= node.point[dim]) {
            rangeSearch(node.right, lower, upper, depth + 1, results)
        }
    }

    /**
     * Calcula a distância euclidiana ao quadrado entre dois pontos.
     *
     * Utiliza a distância ao quadrado para evitar o custo computacional da raiz quadrada
     * nas comparações (a relação de ordem é preservada).
     *
     * Complexidade: O(k).
     *
     * @param a o primeiro ponto.
     * @param b o segundo ponto.
     * @return a distância euclidiana ao quadrado.
     */
    private fun distanceSquared(a: DoubleArray, b: DoubleArray): Double {
        var sum = 0.0
        for (i in 0 until k) {
            val diff = a[i] - b[i]
            sum += diff * diff
        }
        return sum
    }

    /**
     * Verifica se um ponto está dentro de um hiper-retângulo definido por limites inferior e superior.
     *
     * Complexidade: O(k).
     *
     * @param point o ponto a ser testado.
     * @param lower as coordenadas mínimas.
     * @param upper as coordenadas máximas.
     * @return `true` se o ponto estiver dentro da região (inclusive).
     */
    private fun isPointInRange(point: DoubleArray, lower: DoubleArray, upper: DoubleArray): Boolean {
        for (i in 0 until k) {
            if (point[i] < lower[i] || point[i] > upper[i]) return false
        }
        return true
    }

    /**
     * Verifica se dois pontos são iguais (mesmas coordenadas em todas as dimensões).
     *
     * Complexidade: O(k).
     *
     * @param a o primeiro ponto.
     * @param b o segundo ponto.
     * @return `true` se os pontos forem iguais.
     */
    private fun pointsEqual(a: DoubleArray, b: DoubleArray): Boolean {
        for (i in 0 until k) {
            if (a[i] != b[i]) return false
        }
        return true
    }
}
