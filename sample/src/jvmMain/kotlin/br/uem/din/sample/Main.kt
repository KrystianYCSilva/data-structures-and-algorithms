package br.uem.din.sample

import br.uem.din.datastructures.stack.arrayStackOf
import br.uem.din.algorithms.sorting.quickSort
import br.uem.din.optimization.simulatedAnnealing
import br.uem.din.optimization.KnapsackProblem
import br.uem.din.datastructures.graph.AdjacencyList

/**
 * Ponto de entrada para demonstrações práticas da biblioteca.
 * Mostra como instanciar e utilizar os módulos disponíveis.
 */
fun main() {
    println("==================================================")
    println("  Bem-vindo ao algoritmos_otimizacao - Exemplos!  ")
    println("==================================================")
    
    demonstrarEstruturaDeDados()
    demonstrarAlgoritmos()
    demonstrarOtimizacao()
    
    println("==================================================")
    println("         Demonstração finalizada com sucesso!     ")
    println("==================================================")
}

fun demonstrarEstruturaDeDados() {
    println("\n>>> Módulo: DataStructures")
    println("Criando uma Pilha (arrayStackOf)...")
    val stack = arrayStackOf<String>()
    stack.push("Kotlin")
    stack.push("Multiplatform")
    stack.push("Din UEM")
    
    println("Itens na pilha:")
    while (!stack.isEmpty()) {
        println(" - \${stack.pop()}")
    }
    
    println("\nCriando um Grafo (AdjacencyList)...")
    val graph = AdjacencyList<String>()
    val vA = graph.createVertex("A")
    val vB = graph.createVertex("B")
    val vC = graph.createVertex("C")
    graph.addDirectedEdge(vA, vB, 1.0)
    graph.addDirectedEdge(vB, vC, 2.0)
    
    println("Vértices do grafo adicionados com sucesso!")
}

fun demonstrarAlgoritmos() {
    println("\n>>> Módulo: Algorithms")
    val desordenado = mutableListOf(5, 2, 9, 1, 5, 6)
    println("Lista desordenada: \$desordenado")
    
    quickSort(desordenado)
    println("Lista ordenada (QuickSort): \$desordenado")
}

fun demonstrarOtimizacao() {
    println("\n>>> Módulo: Optimization")
    println("Resolvendo Problema da Mochila (Knapsack) com Simulated Annealing...")
    
    val pesos = intArrayOf(2, 3, 5, 7, 1)
    val valores = intArrayOf(10, 15, 30, 40, 8)
    val mochila = KnapsackProblem(weights = pesos, values = valores, capacity = 10)
    
    val resultado = simulatedAnnealing(
        problem = mochila,
        initialTemp = 100.0,
        coolingRate = 0.95,
        minTemp = 1.0,
        maxIterationsPerTemp = 100
    )
    
    println("Instanciando heurística...")
    println("Resultado simulado alcançado com sucesso! Valor ótimo: \${mochila.evaluate(resultado.bestSolution)}")
}
