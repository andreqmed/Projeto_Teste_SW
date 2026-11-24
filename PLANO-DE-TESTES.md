# Plano de testes — Fase 1 (caixa-preta)

Técnicas: **PE** = partição de equivalência, **VL** = valor-limite.
Todos os resultados esperados vêm da especificação (Anexo A), nunca do que o sistema devolve.
Cada linha tem um ID (ex.: `MC-D7`) e a coluna "Teste que cobre" indica o método de teste em `MultaCentavosTest.java`.
Dica: acrescente o ID como comentário na linha correspondente do `@CsvSource`, para ligar plano e teste.

---

## multaCentavos(TipoUsuario tipo, int diasAtraso)

Regras usadas: dias cobrados = `diasAtraso` (aluno, visitante) ou `max(0, diasAtraso - 3)` (professor);
dias cobrados 1–10 a 100 centavos, do 11º em diante a 200 centavos; teto de 5000 centavos;
`diasAtraso` negativo e `tipo` nulo lançam `IllegalArgumentException`.

### Entrada `tipo`

| ID | Classe | Válida? | Valor | Esperado | Teste que cobre |
|---|---|---|---|---|---|
| MC-T1 | ALUNO | válida | `ALUNO` | todos os dias cobrados | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-T2 | PROFESSOR | válida | `PROFESSOR` | 3 dias de carência | `professorTemCarenciaDeTresDias` |
| MC-T3 | VISITANTE | válida | `VISITANTE` | todos os dias cobrados | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-T4 | nulo | inválida | `null` | `IllegalArgumentException` | `tipoNuloLancaExcecao` |

### Entrada `diasAtraso` — aluno e visitante (dias cobrados = diasAtraso)

| ID | Classe ou limite | Valor | Esperado (centavos) | Teste que cobre |
|---|---|---|---|---|
| MC-D1 | PE inválida: negativos (interior) | -10 | `IllegalArgumentException` | `diasAtrasoMuitoNegativoLancaExcecao` |
| MC-D2 | VL: -1 (primeiro inválido) | -1 | `IllegalArgumentException` | `diasAtrasoNegativoNoLimiteLancaExcecao` |
| MC-D3 | VL: 0 (primeiro válido, sem atraso) | 0 | 0 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D4 | VL: 1 (1º dia cobrado) | 1 | 100 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D5 | PE faixa 1 (1–10): interior | 5 | 500 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D6 | VL: 10 (último dia a R$ 1,00) | 10 | 1000 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D7 | VL: 11 (1º dia a R$ 2,00) | 11 | 1200 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D8 | PE faixa 2 (11–30): exemplo do Anexo A | 12 | 1400 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D9 | PE faixa 2 (11–30): interior | 20 | 3000 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D10 | VL: 30 (exatamente no teto) | 30 | 5000 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D11 | VL: 31 (1º valor acima do teto) | 31 | 5000 | `alunoEVisitanteTemTodosOsDiasCobrados` |
| MC-D12 | PE teto (≥ 31): interior | 100 | 5000 | `alunoEVisitanteTemTodosOsDiasCobrados` |

### Entrada `diasAtraso` — professor (dias cobrados = diasAtraso − 3)

Os limites acima se deslocam +3 por causa da carência.

| ID | Classe ou limite | Valor | Esperado (centavos) | Teste que cobre |
|---|---|---|---|---|
| MC-P1 | VL: 0 (sem atraso) | 0 | 0 | `professorTemCarenciaDeTresDias` |
| MC-P2 | PE carência (1–3): interior | 1 | 0 | `professorTemCarenciaDeTresDias` |
| MC-P3 | VL: 3 (último dia de carência) | 3 | 0 | `professorTemCarenciaDeTresDias` |
| MC-P4 | VL: 4 (1º dia cobrado) | 4 | 100 | `professorTemCarenciaDeTresDias` |
| MC-P5 | PE faixa 1: interior | 8 | 500 | `professorTemCarenciaDeTresDias` |
| MC-P6 | VL: 13 (10º dia cobrado) | 13 | 1000 | `professorTemCarenciaDeTresDias` |
| MC-P7 | VL: 14 (11º dia cobrado) | 14 | 1200 | `professorTemCarenciaDeTresDias` |
| MC-P8 | PE faixa 2: 12 dias cobrados (como o exemplo do Anexo A) | 15 | 1400 | `professorTemCarenciaDeTresDias` |
| MC-P9 | VL: 33 (30º dia cobrado, exatamente no teto) | 33 | 5000 | `professorTemCarenciaDeTresDias` |
| MC-P10 | VL: 34 (31º dia cobrado, acima do teto) | 34 | 5000 | `professorTemCarenciaDeTresDias` |
| MC-P11 | PE teto: interior | 200 | 5000 | `professorTemCarenciaDeTresDias` |
| MC-P12 | PE inválida: negativos para professor | -1 e -10 | `IllegalArgumentException` | `diasAtrasoNegativoNoLimiteLancaExcecao`, `diasAtrasoMuitoNegativoLancaExcecao` |

---

## prazoDias — A PREENCHER

## dataDevolucao — A PREENCHER

## limiteEmprestimos — A PREENCHER

## podeEmprestar — A PREENCHER

## podeRenovar — A PREENCHER

## multaTotalCentavos — A PREENCHER
