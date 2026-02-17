package br.uem.din.extensions

import br.uem.din.algorithms.numerical.gcd
import br.uem.din.algorithms.numerical.lcm
import br.uem.din.algorithms.numerical.isPrime

/**
 * Extensões para tipos numéricos integrando algoritmos clássicos.
 */

/**
 * Calcula o Máximo Divisor Comum (GCD) com outro número.
 */
public infix fun Long.gcd(other: Long): Long = gcd(this, other)
public infix fun Int.gcd(other: Int): Int = gcd(this.toLong(), other.toLong()).toInt()

/**
 * Calcula o Mínimo Múltiplo Comum (LCM) com outro número.
 */
public infix fun Long.lcm(other: Long): Long = lcm(this, other)
public infix fun Int.lcm(other: Int): Int = lcm(this.toLong(), other.toLong()).toInt()

/**
 * Verifica se o número é primo.
 */
public fun Long.isPrime(): Boolean = isPrime(this)
public fun Int.isPrime(): Boolean = isPrime(this.toLong())
