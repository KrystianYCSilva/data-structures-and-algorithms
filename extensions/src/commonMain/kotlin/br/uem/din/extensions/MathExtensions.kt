package br.uem.din.extensions

import br.uem.din.algorithms.numerical.gcd
import br.uem.din.algorithms.numerical.lcm
import br.uem.din.algorithms.numerical.isPrime
import br.uem.din.algorithms.numerical.modularExponentiation
import br.uem.din.algorithms.numerical.extendedGcd

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

/**
 * Exponenciação modular. Calcula (this^exp) % mod.
 *
 * @param exp o expoente.
 * @param mod o módulo.
 * @return o resultado da exponenciação modular.
 */
public fun Long.modPow(exp: Long, mod: Long): Long = modularExponentiation(this, exp, mod)
public fun Int.modPow(exp: Int, mod: Int): Int = modularExponentiation(this.toLong(), exp.toLong(), mod.toLong()).toInt()

/**
 * Algoritmo Estendido de Euclides.
 *
 * Encontra x e y tais que (this * x) + (other * y) = gcd(this, other).
 *
 * @param other o outro número.
 * @return Triple contendo (gcd, x, y).
 */
public infix fun Long.extendedGcd(other: Long): Triple<Long, Long, Long> = extendedGcd(this, other)
