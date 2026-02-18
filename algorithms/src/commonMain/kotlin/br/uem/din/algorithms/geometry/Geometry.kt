package br.uem.din.algorithms.geometry

import kotlin.math.atan2

/**
 * Ponto 2D com coordenadas Double.
 */
public data class Point2D(val x: Double, val y: Double) : Comparable<Point2D> {
    override fun compareTo(other: Point2D): Int {
        if (this.x != other.x) return this.x.compareTo(other.x)
        return this.y.compareTo(other.y)
    }
}

/**
 * Convex Hull (Fecho Convexo) usando algoritmo Graham Scan.
 *
 * Encontra o menor polígono convexo que contém todos os pontos do conjunto.
 *
 * @param points lista de pontos 2D.
 * @return lista de pontos que formam o fecho convexo (em ordem anti-horária).
 *
 * Complexidade: O(n log n) devido à ordenação polar.
 */
public fun convexHull(points: List<Point2D>): List<Point2D> {
    if (points.size <= 2) return points

    // 1. Encontrar o ponto mais baixo (e mais a esquerda em caso de empate)
    val start = points.minWithOrNull(Comparator { p1, p2 ->
        if (p1.y != p2.y) p1.y.compareTo(p2.y) else p1.x.compareTo(p2.x)
    })!!

    // 2. Ordenar os pontos pelo ângulo polar em relação ao start
    // Remove o start da lista para ordenar o resto
    val sortedPoints = points.filter { it != start }.sortedWith(Comparator { p1, p2 ->
        val o = orientation(start, p1, p2)
        if (o == 0) {
            // Se colineares, pega o mais distante
            val d1 = distSq(start, p1)
            val d2 = distSq(start, p2)
            d1.compareTo(d2)
        } else {
            // 2 se anti-horário (p2 à esquerda de p1), 1 se horário
            if (o == 2) -1 else 1
        }
    }).toMutableList()

    // Filtra pontos colineares mantendo apenas o mais distante (opcional, mas bom para robustez)
    // Para simplificar, o Graham Scan padrão usa pilha.

    val stack = ArrayDeque<Point2D>()
    stack.add(start)
    if (sortedPoints.isNotEmpty()) stack.add(sortedPoints[0])

    for (i in 1 until sortedPoints.size) {
        while (stack.size >= 2) {
            val top = stack.removeLast()
            val nextToTop = stack.last()
            if (orientation(nextToTop, top, sortedPoints[i]) == 2) { // 2 = CounterClockwise
                stack.add(top) // Keep it
                break
            }
            // Se não for CCW (é horário ou colinear), descarta top e tenta de novo com o novo top
        }
        stack.add(sortedPoints[i])
    }
    
    // Pequeno ajuste para garantir que todos os pontos são verificados corretamente,
    // o loop acima pode ser simplificado. Vamos usar a versão clássica robusta.
    
    val hull = ArrayDeque<Point2D>()
    hull.add(start)
    
    // Tratamento de colineares no início: se houver pontos colineares com start, 
    // a ordenação já cuidou (pegou distantes primeiro se configurado corretamente, 
    // ou precisamos remover os próximos se quisermos apenas vértices extremos).
    // Aqui assumimos que queremos apenas os vértices extremos.
    
    for (point in sortedPoints) {
        while (hull.size >= 2) {
            val top = hull.removeLast()
            val nextToTop = hull.last()
            val o = orientation(nextToTop, top, point)
            if (o == 2) { // Counter-Clockwise (curva à esquerda)
                hull.add(top)
                break
            }
        }
        hull.add(point)
    }

    return hull.toList()
}

/**
 * Calcula a orientação do trio ordenado (p, q, r).
 * 0 -> Colineares
 * 1 -> Horário (Clockwise)
 * 2 -> Anti-horário (Counter-Clockwise)
 */
private fun orientation(p: Point2D, q: Point2D, r: Point2D): Int {
    val result = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
    if (result == 0.0) return 0
    return if (result > 0) 1 else 2
}

private fun distSq(p1: Point2D, p2: Point2D): Double {
    return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)
}

/**
 * Verifica se dois segmentos de reta p1q1 e p2q2 se interceptam.
 */
public fun doIntersect(p1: Point2D, q1: Point2D, p2: Point2D, q2: Point2D): Boolean {
    val o1 = orientation(p1, q1, p2)
    val o2 = orientation(p1, q1, q2)
    val o3 = orientation(p2, q2, p1)
    val o4 = orientation(p2, q2, q1)

    if (o1 != o2 && o3 != o4) return true

    // Casos especiais (colineares)
    if (o1 == 0 && onSegment(p1, p2, q1)) return true
    if (o2 == 0 && onSegment(p1, q2, q1)) return true
    if (o3 == 0 && onSegment(p2, p1, q2)) return true
    if (o4 == 0 && onSegment(p2, q1, q2)) return true

    return false
}

private fun onSegment(p: Point2D, i: Point2D, r: Point2D): Boolean {
    return i.x <= kotlin.math.max(p.x, r.x) && i.x >= kotlin.math.min(p.x, r.x) &&
           i.y <= kotlin.math.max(p.y, r.y) && i.y >= kotlin.math.min(p.y, r.y)
}
