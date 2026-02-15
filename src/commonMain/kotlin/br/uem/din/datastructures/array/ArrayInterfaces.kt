package br.uem.din.datastructures.array

typealias TypeArray<T> = Array<T>

value class Vector<T> private constructor(val array: Array<T>) {
}

value class Matrix<T>(val array: Array<Array<T>>) {
}

