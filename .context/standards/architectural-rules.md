---
description: Architectural rules based on actual project patterns and conventions.
---

# Architectural Rules

## R1: Common-First Development

Todo codigo de algoritmos e estruturas de dados vai em `src/commonMain/kotlin/br/uem/din/`. Codigo platform-specific (jvmMain, jsMain, nativeMain) e usado apenas para `actual` declarations quando a implementacao depende de APIs da plataforma.

## R2: expect/actual para Adaptacao de Plataforma

Quando uma estrutura precisa de implementacao platform-specific, usar `expect class` em commonMain e `actual class` em cada target. Exemplos atuais: `ArrayStack<T>` (JVM usa `java.util.ArrayDeque`), `BitSet`.

## R3: Separacao Read-Only / Mutable

Seguir o padrao Kotlin stdlib: interfaces read-only (`Stack<T>`, `Queue<T>`) separadas de interfaces mutaveis (`MutableStack<T>`, `MutableQueue<T>`). Ambas no mesmo arquivo `*Interfaces.kt`.

## R4: Null sobre Excecoes

Operacoes em colecoes vazias (`pop()`, `peek()`, `dequeue()`) retornam `T?` em vez de lancar excecoes. Sem hierarquia de excecoes customizadas.

## R5: Zero Dependencias Externas

O projeto usa apenas Kotlin stdlib e `kotlin("test")`. Nenhuma dependencia externa (sem kotlinx.*, sem Arrow, sem Kotest).

## R6: Documentacao Academica

KDoc em portugues com tabela de complexidade Big-O, `@param`/`@return`/`@see`, e referencias a Cormen (CLRS), Knuth (TAOCP) ou papers originais.

## R7: Um Tipo Principal por Arquivo

Cada arquivo contem um tipo principal. Nome do arquivo corresponde ao tipo (e.g., `ArrayStack.kt`, `BubbleSort.kt`). Interfaces agrupadas em `*Interfaces.kt`.
