---
description: Project overview, goals, scope, and current status.
---

# Project Overview

Resumo do projeto para tarefas Deliberado+.

## What

Biblioteca academica Kotlin Multiplatform para estruturas de dados, algoritmos classicos e heuristicas de otimizacao, com foco em API didatica, portabilidade e qualidade de testes.

## Why

Concentrar em um unico projeto KMP implementacoes de referencia para estudo, ensino e experimentacao, evitando dependencia de bibliotecas externas e mantendo comportamento consistente entre JVM, JS e Native.

## Scope

### In scope

- Estruturas de dados (36)
- Algoritmos classicos (46)
- Heuristicas/meta-heuristicas (12)
- Framework de modelagem de problemas de otimizacao (7 modelagens)
- Publicacao modular em Maven (`datastructures`, `algorithms`, `extensions`, `optimization`, `bom`)
- Modulo de exemplos e casos de uso para rapida experimentacao (`sample`)

### Out of scope

- Aplicacoes web/API REST
- Interface grafica
- Infra de deploy de aplicacao final

## Identidade

- Nome: `algoritmos_otimizacao`
- Root project name (Gradle): `algoritmos-otimizacao`
- Tipo: biblioteca academica Kotlin Multiplatform
- Dominios: estruturas de dados, algoritmos classicos, heuristicas de otimizacao

## Escopo implementado

- 36 estruturas de dados
- 46 algoritmos classicos
- 12 heuristicas/meta-heuristicas
- 7 modelagens de problema

## Status

- Fase 1: completa
- Fase 2: parcial (faltam 7 algoritmos planejados)
- Fase 3: completa
- Build: verde em JVM + JS + Native
- Publicacao: `group=br.uem.din`, `version=0.1.0`

## Arquitetura

- Multi-modulo Gradle: `:datastructures`, `:algorithms`, `:extensions`, `:optimization`, `:bom`, `:sample`

## Links internos

- Roadmap: `docs/PROJECT_ROADMAP.md`
- Navegacao do contexto: `../README.md`
