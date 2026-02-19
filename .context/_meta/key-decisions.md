---
description: Architectural rules and ADR summary for this repository.
---

# Key Decisions (ADRs)

## ADRs (resumo)

| Data | ADR | Decisao |
|---|---|---|
| 2026-02 | ADR-001 | Adotar arquitetura multi-modulo (`datastructures`, `algorithms`, `extensions`, `optimization`, `bom`) |
| 2026-02 | ADR-002 | Manter `:optimization` desacoplado de DS/algorithms |
| 2026-02 | ADR-003 | Padrao API imutavel/mutavel + `explicitApi()` em todos os modulos |

## Regras essenciais

1. Codigo novo deve respeitar ownership de modulo
2. Evitar dependencia circular entre modulos
3. Usar `kotlin.test` apenas para testes
4. Manter API publica explicita (`explicitApi`)
5. Seguir padrao de interfaces imutavel/mutavel
6. Operacoes em vazio retornam `null` quando aplicavel
7. KDoc em portugues para API publica

## Verificacao obrigatoria

- Rodar `gradlew.bat check` antes de afirmar que alteracao esta valida

## Links internos

- Regras arquiteturais: `../standards/architectural-rules.md`
- Padrao de testes: `../standards/testing-strategy.md`
- Qualidade de codigo: `../standards/code-quality.md`
