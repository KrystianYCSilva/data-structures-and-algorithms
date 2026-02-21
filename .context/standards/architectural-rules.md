---
description: Architectural rules based on current multi-module project structure.
---

# Architectural Rules

## R1: Module Ownership

- Estruturas de dados: `datastructures/src/commonMain/kotlin/br/uem/din/datastructures/`
- Algoritmos classicos: `algorithms/src/commonMain/kotlin/br/uem/din/algorithms/`
- Extensoes: `extensions/src/commonMain/kotlin/br/uem/din/extensions/`
- Heuristicas/modelagens: `optimization/src/commonMain/kotlin/br/uem/din/optimization/`
- Exemplos de uso: `sample/src/commonMain/kotlin/br/uem/din/sample/`

## R2: Dependency Direction

- `:datastructures` nao depende de modulos internos
- `:algorithms` depende de `:datastructures`
- `:extensions` depende de `:datastructures` e `:algorithms`
- `:optimization` e independente
- `:sample` depende de `:datastructures`, `:algorithms` e `:optimization` (nao expoe dependencias transitivas, usado apenas para demonstracao)
- Nao introduzir dependencia circular

## R3: Common-first + expect/actual minimo

- Priorizar implementacao em `commonMain`
- Usar `expect`/`actual` apenas quando houver necessidade de API especifica de plataforma
- Elementos atuais com adaptacao de plataforma residem em `:datastructures`

## R4: API Imutavel/Mutavel

- Seguir padrao Kotlin stdlib
- Onde houver conflito com classe concreta: `Immutable*` / `Mutable*`
- Onde nao houver conflito: substantivo puro / `Mutable*`

## R5: Semantica de erro

- Operacoes em estrutura vazia retornam `null` quando aplicavel (`pop`, `peek`, `dequeue`)
- `assertFailsWith` nos testes para precondicoes invalidas

## R6: Documentacao academica

- KDoc em portugues para API publica
- Tabela de complexidade + referencias classicas (CLRS/TAOCP/papers)

## R7: Publicacao por modulo

- Cada modulo possui `maven-publish` e POM completo
- `:bom` concentra alinhamento de versao
