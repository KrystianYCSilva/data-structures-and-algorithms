# Publicacao (Release)

Este guia descreve o fluxo para publicar os artefatos do projeto.

## Artefatos

- `br.uem.din:datastructures:0.1.0`
- `br.uem.din:algorithms:0.1.0`
- `br.uem.din:extensions:0.1.0`
- `br.uem.din:optimization:0.1.0`
- `br.uem.din:bom:0.1.0`

## Pre-requisitos

Defina credenciais e assinatura por `gradle.properties` local (nao versionado) ou variaveis de ambiente:

- `ossrhUsername` / `OSSRH_USERNAME`
- `ossrhPassword` / `OSSRH_PASSWORD`
- `signingKey` / `SIGNING_KEY`
- `signingPassword` / `SIGNING_PASSWORD`

## Fluxo recomendado

1. Validacao de release:

```bash
./gradlew.bat releasePreflight
```

2. Build e testes completos:

```bash
./gradlew.bat clean check
```

3. Validacao de consumo local:

```bash
./gradlew.bat publishToMavenLocal
```

4. Publicacao remota:

```bash
./gradlew.bat publish
```

## Checklist

- [ ] Versao correta em todos os modulos
- [ ] CHANGELOG atualizado
- [ ] README e docs coerentes com a release
- [ ] `check` verde em JVM/JS/Native
- [ ] Assinatura habilitada e credenciais configuradas
