# Meus Gastos — MVVM

App Android de controle de gastos pessoais, implementado seguindo o tutorial **"Meus Gastos — Um app completo em arquitetura MVVM"** (Programação Mobile · UNASP-SP · 2026/2).

Cadastrar uma despesa (descrição, valor, categoria), ver a lista, filtrar por categoria, remover e acompanhar o total. Os dados ficam em memória.

## Como rodar

1. Abra a pasta no Android Studio e clique em **Sync Now**.
2. Rode em um emulador ou dispositivo (minSdk 24).

O app abre com 4 gastos de exemplo (`GastoRepositoryDemo`, Etapa 6). Para começar com a lista vazia, troque para `GastoRepositoryMemoria()` no construtor do `GastosViewModel`.

## Entregáveis (Etapa 8)

| Entregável | Status | Onde está |
| --- | --- | --- |
| Um commit por etapa ("etapa N") | Entregue | Branch `main`: projeto inicial + etapa 1 a etapa 8 |
| App funcionando, com as três pastas separadas | Entregue | `app/src/main/java/br/unasp/meusgastos/` → `model/`, `viewmodel/`, `ui/` |
| Cinco `@Preview`, pelo menos três sem estado | Entregue | Final de `ui/TelaGastos.kt` (os cinco são de componentes sem estado) |
| `ARQUITETURA.md` com as três perguntas | Entregue | [ARQUITETURA.md](ARQUITETURA.md) |
| `ESTRAGOS.md` com os cinco estragos | Em andamento | [ESTRAGOS.md](ESTRAGOS.md): previsões prontas; resultados observados a preencher |

## Commits por etapa

| Mensagem | O que entrou |
| --- | --- |
| projeto inicial | Empty Activity (Kotlin DSL), package `br.unasp.meusgastos`, dependência `lifecycle-viewmodel-compose` 2.10.0 |
| etapa 1 | `Categoria` (enum) e `Gasto` (data class com `id`) |
| etapa 2 | Interface `GastoRepository` e implementação `GastoRepositoryMemoria` |
| etapa 3 | `GastosViewModel`: estado com `private set`, estado derivado (`gastosVisiveis`, `total`, `quantidade`), validações em `adicionar()` |
| etapa 4 | `TelaGastos` e os componentes `FormularioGasto`, `FiltroCategorias`, `ResumoTotal`, `LinhaGasto`, `Etiqueta` |
| etapa 5 | Cinco `@Preview` |
| etapa 6 | `GastoRepositoryDemo` com 4 gastos; o ViewModel passa a usá-lo. Nenhum arquivo de `ui/` mudou |
| etapa 7 | `ESTRAGOS.md` |
| etapa 8 | `ARQUITETURA.md` |

## Arquitetura

As dependências apontam só para dentro: **ui → viewmodel → model**.

```
br.unasp.meusgastos
├── model/        ← os dados e de onde eles vêm
│   ├── Categoria.kt
│   ├── Gasto.kt
│   └── GastoRepository.kt   (interface + Memoria + Demo)
├── viewmodel/    ← o estado da tela e as regras
│   └── GastosViewModel.kt
└── ui/           ← o que aparece na tela
    └── TelaGastos.kt
```

| Camada | Importa do app |
| --- | --- |
| model | nada (Kotlin puro) |
| viewmodel | só `model` (nenhum Compose UI, Context ou Toast) |
| ui | `viewmodel` e `model` |

**Prova da Etapa 6:** trocar `GastoRepositoryMemoria` por `GastoRepositoryDemo` alterou só `GastoRepository.kt` e o construtor do ViewModel; nenhum arquivo de `ui/` mudou.

## Previews

| Preview | Componente | O que mostra |
| --- | --- | --- |
| `ResumoTotalPreview` | `ResumoTotal` | Total R$ 201,70 com 4 gastos |
| `LinhaGastoPreview` | `LinhaGasto` | "Almoço no bandejão", R$ 18,50, Alimentação |
| `FormularioComErroPreview` | `FormularioGasto` | Formulário com o erro "O valor precisa ser um número" |
| `FiltroCategoriasPreview` | `FiltroCategorias` | Filtro com Transporte selecionado |
| `EtiquetaPreview` | `Etiqueta` | Etiqueta da categoria Moradia |

## Uso de IA

O código das Etapas 1 a 6 foi gerado com o Claude a partir do tutorial; os commits trazem a linha `Co-Authored-By: Claude`. Os componentes de UI são uma versão própria, pois o arquivo `03_ui_TelaGastos.kt` do AVA não estava disponível. As respostas do `ARQUITETURA.md` e as previsões do `ESTRAGOS.md` são do aluno. Uso registrado no Diário de IA.
