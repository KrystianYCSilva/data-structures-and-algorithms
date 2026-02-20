# Contributing

Obrigado por querer contribuir com `algoritmos_otimizacao`.

## Como ajudar

- Corrigindo bugs e regressions
- Adicionando testes (principalmente `commonTest`)
- Melhorando KDoc e exemplos de uso
- Implementando algoritmos faltantes do roadmap
- Revisando complexidades e referencias academicas

## Regras tecnicas

- Linguagem: Kotlin Multiplatform
- Testes: `kotlin.test` (sem Kotest/JUnit direto)
- Estilo: `kotlin.code.style=official`
- API: manter separacao imutavel/mutavel conforme padrao do projeto

## Fluxo recomendado

1. Crie branch de feature/fix
2. Implemente com foco em `commonMain`
3. Adicione testes em `commonTest` (e platform tests quando necessario)
4. Rode validacoes locais:

```bash
./gradlew.bat check
```

5. Atualize docs afetadas (`docs/ALGORITHM_CATALOG.md`, `docs/USAGE_EXAMPLES.md`, `docs/PROJECT_ROADMAP.md`)
6. Abra PR com contexto e motivacao tecnica

## Padrao para novos algoritmos

- KDoc em portugues
- Complexidade temporal e espacial explicitas
- Referencia academica (CLRS, TAOCP ou paper original)
- Testes de fronteira + aleatorizados com seed quando aplicavel

## Report de bugs

Inclua:

- Comportamento esperado vs observado
- Reproducao minima
- Plataforma alvo (JVM/JS/Native)
- Versao usada
