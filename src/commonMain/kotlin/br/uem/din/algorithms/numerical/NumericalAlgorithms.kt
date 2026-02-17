package br.uem.din.algorithms.numerical

import kotlin.math.sqrt

/**
 * Máximo Divisor Comum (GCD) - Algoritmo de Euclides.
 *
 * @param a primeiro número.
 * @param b segundo número.
 * @return o MDC de a e b.
 *
 * Complexidade: O(log(min(a,b)))
 */
public tailrec fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

/**
 * Mínimo Múltiplo Comum (LCM).
 *
 * @param a primeiro número.
 * @param b segundo número.
 * @return o MMC de a e b.
 */
public fun lcm(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) return 0
    return (a / gcd(a, b)) * b // Divide primeiro para evitar overflow
}

/**
 * Algoritmo Estendido de Euclides.
 *
 * Encontra x e y tais que ax + by = gcd(a, b).
 *
 * @param a primeiro número.
 * @param b segundo número.
 * @return Triple(gcd, x, y).
 */
public fun extendedGcd(a: Long, b: Long): Triple<Long, Long, Long> {
    if (b == 0L) {
        return Triple(a, 1L, 0L)
    }
    val (d, x1, y1) = extendedGcd(b, a % b)
    val x = y1
    val y = x1 - y1 * (a / b)
    return Triple(d, x, y)
}

/**
 * Exponenciação Rápida (Modular Exponentiation).
 *
 * Calcula (base^exp) % mod.
 *
 * @param base base.
 * @param exp expoente.
 * @param mod módulo.
 * @return resultado da exponenciação modular.
 *
 * Complexidade: O(log exp)
 */
public fun modularExponentiation(base: Long, exp: Long, mod: Long): Long {
    var res = 1L
    var b = base % mod
    var e = exp
    while (e > 0) {
        if (e % 2 == 1L) res = (res * b) % mod
        b = (b * b) % mod
        e /= 2
    }
    return res
}

/**
 * Crivo de Eratóstenes (Sieve of Eratosthenes).
 *
 * Encontra todos os números primos até n.
 *
 * @param n limite superior (inclusivo).
 * @return lista de números primos até n.
 *
 * Complexidade: O(n log log n)
 */
public fun sieveOfEratosthenes(n: Int): List<Int> {
    val prime = BooleanArray(n + 1) { true }
    prime[0] = false
    prime[1] = false

    for (p in 2..sqrt(n.toDouble()).toInt()) {
        if (prime[p]) {
            for (i in p * p..n step p) {
                prime[i] = false
            }
        }
    }

    val primes = mutableListOf<Int>()
    for (i in 2..n) {
        if (prime[i]) {
            primes.add(i)
        }
    }
    return primes
}

/**
 * Teste de Primalidade (Trial Division).
 *
 * @param n número a testar.
 * @return `true` se n for primo.
 *
 * Complexidade: O(sqrt(n))
 */
public fun isPrime(n: Long): Boolean {
    if (n <= 1) return false
    if (n <= 3) return true
    if (n % 2 == 0L || n % 3 == 0L) return false
    var i = 5L
    while (i * i <= n) {
        if (n % i == 0L || n % (i + 2) == 0L) return false
        i += 6
    }
    return true
}
