package br.uem.din

expect class Platform() {
    val name: String
}

class Optimizer {
    fun run(): String {
        return "Running optimization on ${Platform().name}"
    }
}
