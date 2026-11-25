# Plano de Testes — Sistema de Biblioteca (Fase 1: Caixa-preta)

## 1. Objetivo e Estratégia

Este plano documenta as classes de equivalência (válidas e inválidas), os valores-limite de cada fronteira e os casos de teste JUnit 5 correspondentes para os sete métodos da classe `RegrasBiblioteca` especificados no Anexo A.

Todos os oráculos foram derivados estritamente da especificação do Anexo A. Os testes encontram-se em `src/test/java/br/edu/biblioteca/`.

---

## 2. Mapeamento por Método: Classes de Equivalência, Valores-Limite e Testes

### 2.1. `prazoDias(TipoUsuario tipo)`

**Regra (Anexo A):** Devolve o prazo de empréstimo: 14 dias para `ALUNO`, 30 para `PROFESSOR` e 7 para `VISITANTE`. `tipo` nulo lança `IllegalArgumentException`.

#### Classes de Equivalência
| ID | Entrada (`tipo`) | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-PD-V1 | `TipoUsuario.ALUNO` | Válida | `14` | `PrazoDiasTest.prazoCorretoParaCadaTipoDeUsuario` (`[1] ALUNO, 14`) e `ExemploTest.alunoTemPrazoDe14Dias` |
| CE-PD-V2 | `TipoUsuario.PROFESSOR` | Válida | `30` | `PrazoDiasTest.prazoCorretoParaCadaTipoDeUsuario` (`[2] PROFESSOR, 30`) |
| CE-PD-V3 | `TipoUsuario.VISITANTE` | Válida | `7` | `PrazoDiasTest.prazoCorretoParaCadaTipoDeUsuario` (`[3] VISITANTE, 7`) |
| CE-PD-I1 | `null` | Inválida | `IllegalArgumentException` | `PrazoDiasTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
- O parâmetro `tipo` é um `enum` discreto (`ALUNO`, `PROFESSOR`, `VISITANTE`), sem domínio numérico ordenado; cada valor do domínio discreto e a referência `null` são testados diretamente.

---

### 2.2. `dataDevolucao(LocalDate retirada, TipoUsuario tipo)`

**Regra (Anexo A):** Soma o prazo do tipo à data de retirada. Se o resultado cair num domingo, a devolução passa para a segunda-feira seguinte. O sábado é dia normal. Data de retirada nula ou `tipo` nulo lançam `IllegalArgumentException`.

#### Classes de Equivalência
| ID | Entrada (`retirada`, `tipo`) | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-DD-V1 | `retirada` válida, `ALUNO`, `retirada + 14` cai de segunda a sexta | Válida | `retirada + 14 dias` (`2026-11-18`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[1] 2026-11-04, ALUNO, 2026-11-18`) |
| CE-DD-V2 | `retirada` válida, `ALUNO`, `retirada + 14` cai em sábado | Válida | Permanece no sábado (`2026-11-14`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[2] 2026-10-31, ALUNO, 2026-11-14`) |
| CE-DD-V3 | `retirada` válida, `ALUNO`, `retirada + 14` cai em domingo | Válida | Passa para segunda (`2026-11-16`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[3] 2026-11-01, ALUNO, 2026-11-16`) |
| CE-DD-V4 | `retirada` válida, `PROFESSOR`, `retirada + 30` cai de segunda a sexta | Válida | `retirada + 30 dias` (`2026-12-01`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[4] 2026-11-01, PROFESSOR, 2026-12-01`) |
| CE-DD-V5 | `retirada` válida, `PROFESSOR`, `retirada + 30` cai em sábado | Válida | Permanece no sábado (`2026-12-05`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[5] 2026-11-05, PROFESSOR, 2026-12-05`) |
| CE-DD-V6 | `retirada` válida, `PROFESSOR`, `retirada + 30` cai em domingo | Válida | Passa para segunda (`2026-12-07`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[6] 2026-11-06, PROFESSOR, 2026-12-07`) |
| CE-DD-V7 | `retirada` válida, `VISITANTE`, `retirada + 7` cai de segunda a sexta | Válida | `retirada + 7 dias` (`2026-11-11`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[7] 2026-11-04, VISITANTE, 2026-11-11`) |
| CE-DD-V8 | `retirada` válida, `VISITANTE`, `retirada + 7` cai em sábado | Válida | Permanece no sábado (`2026-11-07`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[8] 2026-10-31, VISITANTE, 2026-11-07`) |
| CE-DD-V9 | `retirada` válida, `VISITANTE`, `retirada + 7` cai em domingo | Válida | Passa para segunda (`2026-11-09`) | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[9] 2026-11-01, VISITANTE, 2026-11-09`) |
| CE-DD-I1 | `retirada == null`, `tipo` válido | Inválida | `IllegalArgumentException` | `DataDevolucaoTest.dataRetiradaNulaLancaIllegalArgumentException` |
| CE-DD-I2 | `retirada` válida, `tipo == null` | Inválida | `IllegalArgumentException` | `DataDevolucaoTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
| ID | Fronteira | Valores-Limite Testados | Teste que Cobre |
| --- | --- | --- | --- |
| VL-DD-1 | Fronteira do fim de semana na data calculada (Sábado vs. Domingo) | Data base caindo no **sábado** (sem ajuste) vs. **domingo** (ajuste `+1` dia para segunda-feira) para `ALUNO`, `PROFESSOR` e `VISITANTE` | `DataDevolucaoTest.calculaDataDevolucaoCorretamente` (`[2]` e `[3]` para `ALUNO`; `[5]` e `[6]` para `PROFESSOR`; `[8]` e `[9]` para `VISITANTE`) |

---

### 2.3. `limiteEmprestimos(TipoUsuario tipo)`

**Regra (Anexo A):** Devolve quantos empréstimos simultâneos o usuário pode ter: 3 para `ALUNO`, 8 para `PROFESSOR` e 1 para `VISITANTE`. `tipo` nulo lança `IllegalArgumentException`.

#### Classes de Equivalência
| ID | Entrada (`tipo`) | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-LE-V1 | `TipoUsuario.ALUNO` | Válida | `3` | `LimiteEmprestimosTest.limiteCorretoParaCadaTipoDeUsuario` (`[1] ALUNO, 3`) |
| CE-LE-V2 | `TipoUsuario.PROFESSOR` | Válida | `8` | `LimiteEmprestimosTest.limiteCorretoParaCadaTipoDeUsuario` (`[2] PROFESSOR, 8`) |
| CE-LE-V3 | `TipoUsuario.VISITANTE` | Válida | `1` | `LimiteEmprestimosTest.limiteCorretoParaCadaTipoDeUsuario` (`[3] VISITANTE, 1`) |
| CE-LE-I1 | `null` | Inválida | `IllegalArgumentException` | `LimiteEmprestimosTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
- Domínio discreto (`enum TipoUsuario` + `null`), totalmente coberto pelos 4 testes acima.

---

### 2.4. `multaCentavos(TipoUsuario tipo, int diasAtraso)`

**Regra (Anexo A):**
- `PROFESSOR` tem carência: os 3 primeiros dias de atraso não são cobrados (`diasCobrados = max(0, diasAtraso - 3)`). Para `ALUNO` e `VISITANTE`, todos os dias são cobrados (`diasCobrados = diasAtraso`).
- Do 1º ao 10º dia cobrado: R$ 1,00 (`100` centavos) por dia. Do 11º dia cobrado em diante: R$ 2,00 (`200` centavos) por dia.
- Teto por empréstimo: R$ 50,00 (`5000` centavos), atingido com 30 dias cobrados (`10 * 100 + 20 * 200 = 5000`), isto é, `diasAtraso = 30` para `ALUNO`/`VISITANTE` e `diasAtraso = 33` para `PROFESSOR`.
- `diasAtraso < 0` ou `tipo == null` lançam `IllegalArgumentException`.

#### Classes de Equivalência
| ID | Condição de Entrada | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-MC-V1 | `ALUNO` / `VISITANTE`, sem atraso (`diasAtraso == 0`) | Válida | `0` | `MultaCentavosTest.multaCorretaParaAlunoEVisitante` (`ALUNO, 0` e `VISITANTE, 0`) |
| CE-MC-V2 | `ALUNO` / `VISITANTE`, 1ª faixa de cobrança (`1 <= diasAtraso <= 10`) | Válida | `100 * diasAtraso` | `MultaCentavosTest.multaCorretaParaAlunoEVisitante` (`diasAtraso = 1, 5, 9, 10`) |
| CE-MC-V3 | `ALUNO` / `VISITANTE`, 2ª faixa abaixo do teto (`11 <= diasAtraso <= 29`) | Válida | `1000 + 200 * (diasAtraso - 10)` | `MultaCentavosTest.multaCorretaParaAlunoEVisitante` (`diasAtraso = 11, 12, 20, 29`) |
| CE-MC-V4 | `ALUNO` / `VISITANTE`, no teto ou acima (`diasAtraso >= 30`) | Válida | `5000` | `MultaCentavosTest.multaCorretaParaAlunoEVisitante` (`diasAtraso = 30, 31, 40`) |
| CE-MC-V5 | `PROFESSOR`, sem atraso ou dentro da carência (`0 <= diasAtraso <= 3`) | Válida | `0` | `MultaCentavosTest.multaCorretaParaProfessor` (`diasAtraso = 0, 1, 2, 3`) |
| CE-MC-V6 | `PROFESSOR`, 1ª faixa após carência (`4 <= diasAtraso <= 13`) | Válida | `100 * (diasAtraso - 3)` | `MultaCentavosTest.multaCorretaParaProfessor` (`diasAtraso = 4, 8, 12, 13`) |
| CE-MC-V7 | `PROFESSOR`, 2ª faixa abaixo do teto (`14 <= diasAtraso <= 32`) | Válida | `1000 + 200 * (diasAtraso - 13)` | `MultaCentavosTest.multaCorretaParaProfessor` (`diasAtraso = 14, 15, 23, 32`) |
| CE-MC-V8 | `PROFESSOR`, no teto ou acima (`diasAtraso >= 33`) | Válida | `5000` | `MultaCentavosTest.multaCorretaParaProfessor` (`diasAtraso = 33, 34, 40`) |
| CE-MC-I1 | `diasAtraso < 0` (para `ALUNO`, `PROFESSOR`, `VISITANTE`) | Inválida | `IllegalArgumentException` | `MultaCentavosTest.diasAtrasoNegativosLancaIllegalArgumentException` (`-1`, `-10`) |
| CE-MC-I2 | `tipo == null` | Inválida | `IllegalArgumentException` | `MultaCentavosTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
| ID | Fronteira | Valores-Limite Testados | Teste que Cobre |
| --- | --- | --- | --- |
| VL-MC-1 | Fronteira de validade de `diasAtraso` (`< 0` vs `>= 0`) | `-1`, `0`, `1` (para todos os tipos) | `diasAtrasoNegativosLancaIllegalArgumentException` (`-1`), `multaCorretaParaAlunoEVisitante` (`0, 1`), `multaCorretaParaProfessor` (`0, 1`) |
| VL-MC-2 | Fim da carência de `PROFESSOR` (`<= 3` vs `>= 4` dias) | `2`, `3` (`0` centavos) e `4` (`100` centavos) | `MultaCentavosTest.multaCorretaParaProfessor` (`2, 3, 4`) |
| VL-MC-3 | Mudança de faixa diária para `ALUNO` e `VISITANTE` (10º vs 11º dia cobrado) | `9` (`900`), `10` (`1000`), `11` (`1200`), `12` (`1400`) | `MultaCentavosTest.multaCorretaParaAlunoEVisitante` (`9, 10, 11, 12`) |
| VL-MC-4 | Mudança de faixa diária para `PROFESSOR` (10º vs 11º dia cobrado -> 13º vs 14º dia de atraso) | `12` (`900`), `13` (`1000`), `14` (`1200`), `15` (`1400`) | `MultaCentavosTest.multaCorretaParaProfessor` (`12, 13, 14, 15`) |
| VL-MC-5 | Teto de R$ 50,00 (`5000` centavos) para `ALUNO` e `VISITANTE` (29, 30, 31 dias) | `29` (`4800`), `30` (`5000`), `31` (`5000`) | `MultaCentavosTest.multaCorretaParaAlunoEVisitante` (`29, 30, 31`) |
| VL-MC-6 | Teto de R$ 50,00 (`5000` centavos) para `PROFESSOR` (32, 33, 34 dias) | `32` (`4800`), `33` (`5000`), `34` (`5000`) | `MultaCentavosTest.multaCorretaParaProfessor` (`32, 33, 34`) |

---

### 2.5. `podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)`

**Regra (Anexo A):** Devolve `true` somente se as três condições valerem juntas:
1. `emprestimosAtivos < limiteEmprestimos(tipo)` (3 para `ALUNO`, 8 para `PROFESSOR`, 1 para `VISITANTE`);
2. `multaPendenteCentavos <= 1000` (no máximo R$ 10,00);
3. `possuiAtraso == false`.
`emprestimosAtivos < 0`, `multaPendenteCentavos < 0` ou `tipo == null` lançam `IllegalArgumentException`.

#### Classes de Equivalência
| ID | Condição de Entrada | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-PE-V1 | `ALUNO`, `0 <= emprestimosAtivos < 3`, `0 <= multa <= 1000`, `possuiAtraso == false` | Válida | `true` | `PodeEmprestarTest.emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`[1]` a `[4]`) |
| CE-PE-V2 | `PROFESSOR`, `0 <= emprestimosAtivos < 8`, `0 <= multa <= 1000`, `possuiAtraso == false` | Válida | `true` | `PodeEmprestarTest.emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`[5]` a `[8]`) |
| CE-PE-V3 | `VISITANTE`, `emprestimosAtivos == 0`, `0 <= multa <= 1000`, `possuiAtraso == false` | Válida | `true` | `PodeEmprestarTest.emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`[9]` a `[11]`) |
| CE-PE-V4 | `emprestimosAtivos >= limite` (`ALUNO >= 3`, `PROFESSOR >= 8`, `VISITANTE >= 1`) | Válida | `false` | `PodeEmprestarTest.emprestimoNegadoQuandoLimiteDeEmprestimosAtingidoOuUltrapassado` |
| CE-PE-V5 | `multaPendenteCentavos > 1000` (para todos os tipos) | Válida | `false` | `PodeEmprestarTest.emprestimoNegadoQuandoMultaSuperaLimite` (`1001`, `1500`) |
| CE-PE-V6 | `possuiAtraso == true` (para todos os tipos) | Válida | `false` | `PodeEmprestarTest.emprestimoNegadoQuandoExisteAtraso` e `emprestimoNegadoQuandoLimiteEAtrasoEstaoPresentes` |
| CE-PE-I1 | `emprestimosAtivos < 0` | Inválida | `IllegalArgumentException` | `PodeEmprestarTest.quantidadeDeEmprestimosNegativaLancaIllegalArgumentException` |
| CE-PE-I2 | `multaPendenteCentavos < 0` | Inválida | `IllegalArgumentException` | `PodeEmprestarTest.multaNegativaLancaIllegalArgumentException` |
| CE-PE-I3 | `tipo == null` | Inválida | `IllegalArgumentException` | `PodeEmprestarTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
| ID | Fronteira | Valores-Limite Testados | Teste que Cobre |
| --- | --- | --- | --- |
| VL-PE-1 | Fronteira inferior de `emprestimosAtivos` (`-1` vs `0`) | `-1` (exceção) e `0` (`true`) para `ALUNO`, `PROFESSOR`, `VISITANTE` | `quantidadeDeEmprestimosNegativaLancaIllegalArgumentException` (`-1`) e `emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`0`) |
| VL-PE-2 | Fronteira superior de `emprestimosAtivos` para `ALUNO` (limite = 3) | `2` (`true`), `3` (`false`), `4` (`false`) | `emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`2`) e `emprestimoNegadoQuandoLimiteDeEmprestimosAtingidoOuUltrapassado` (`3, 4`) |
| VL-PE-3 | Fronteira superior de `emprestimosAtivos` para `PROFESSOR` (limite = 8) | `7` (`true`), `8` (`false`), `9` (`false`) | `emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`7`) e `emprestimoNegadoQuandoLimiteDeEmprestimosAtingidoOuUltrapassado` (`8, 9`) |
| VL-PE-4 | Fronteira superior de `emprestimosAtivos` para `VISITANTE` (limite = 1) | `0` (`true`), `1` (`false`), `2` (`false`) | `emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`0`) e `emprestimoNegadoQuandoLimiteDeEmprestimosAtingidoOuUltrapassado` (`1, 2`) |
| VL-PE-5 | Fronteira inferior de `multaPendenteCentavos` (`-1` vs `0`) | `-1` (exceção) e `0` (`true`) | `multaNegativaLancaIllegalArgumentException` (`-1`) e `emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`0`) |
| VL-PE-6 | Fronteira superior de `multaPendenteCentavos` (`<= 1000` vs `> 1000`) | `999` (`true`), `1000` (`true`), `1001` (`false`) para todos os tipos | `emprestimoPermitidoQuandoCondicoesSaoAtendidas` (`999, 1000`) e `emprestimoNegadoQuandoMultaSuperaLimite` (`1001`) |

---

### 2.6. `podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)`

**Regra (Anexo A):**
- `VISITANTE` nunca renova (`false`).
- `ALUNO` renova até 2 vezes (`renovacoesFeitas < 2` -> `0` ou `1`).
- `PROFESSOR` renova até 3 vezes (`renovacoesFeitas < 3` -> `0`, `1` ou `2`).
- Só é possível renovar se `possuiReserva == false` e `emAtraso == false`.
- `renovacoesFeitas < 0` ou `tipo == null` lançam `IllegalArgumentException` (para qualquer tipo).

#### Classes de Equivalência
| ID | Condição de Entrada | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-PR-V1 | `ALUNO`, `0 <= renovacoesFeitas < 2`, `possuiReserva == false`, `emAtraso == false` | Válida | `true` | `PodeRenovarTest.renovacaoPermitidaAbaixoDoLimite` (`ALUNO, 0` e `ALUNO, 1`) |
| CE-PR-V2 | `PROFESSOR`, `0 <= renovacoesFeitas < 3`, `possuiReserva == false`, `emAtraso == false` | Válida | `true` | `PodeRenovarTest.renovacaoPermitidaAbaixoDoLimite` (`PROFESSOR, 0, 1, 2`) |
| CE-PR-V3 | `VISITANTE`, `renovacoesFeitas >= 0`, sem reserva e sem atraso | Válida | `false` | `PodeRenovarTest.visitanteNaoPodeRenovar` (`VISITANTE, 0`) |
| CE-PR-V4 | `ALUNO` com `renovacoesFeitas >= 2` ou `PROFESSOR` com `renovacoesFeitas >= 3` | Válida | `false` | `PodeRenovarTest.renovacaoNegadaQuandoLimiteAtingidoOuUltrapassado` (`ALUNO: 2, 3`; `PROFESSOR: 3, 4`) |
| CE-PR-V5 | `possuiReserva == true` (para qualquer tipo) | Válida | `false` | `PodeRenovarTest.renovacaoNegadaQuandoExisteReserva` |
| CE-PR-V6 | `emAtraso == true` (para qualquer tipo) | Válida | `false` | `PodeRenovarTest.renovacaoNegadaQuandoEmprestimoEstaEmAtraso` |
| CE-PR-I1 | `renovacoesFeitas < 0` (para `ALUNO`, `PROFESSOR`, `VISITANTE`) | Inválida | `IllegalArgumentException` | `PodeRenovarTest.renovacoesNegativasLancaIllegalArgumentException` (`-1`, `-5`) |
| CE-PR-I2 | `tipo == null` | Inválida | `IllegalArgumentException` | `PodeRenovarTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
| ID | Fronteira | Valores-Limite Testados | Teste que Cobre |
| --- | --- | --- | --- |
| VL-PR-1 | Fronteira inferior de `renovacoesFeitas` (`-1` vs `0`) | `-1` (exceção) e `0` (válido) para todos os tipos | `renovacoesNegativasLancaIllegalArgumentException` (`-1`), `renovacaoPermitidaAbaixoDoLimite` (`0`), `visitanteNaoPodeRenovar` (`0`) |
| VL-PR-2 | Fronteira superior de `renovacoesFeitas` para `ALUNO` (limite = 2) | `1` (`true`), `2` (`false`), `3` (`false`) | `renovacaoPermitidaAbaixoDoLimite` (`ALUNO, 1`) e `renovacaoNegadaQuandoLimiteAtingidoOuUltrapassado` (`ALUNO, 2, 3`) |
| VL-PR-3 | Fronteira superior de `renovacoesFeitas` para `PROFESSOR` (limite = 3) | `2` (`true`), `3` (`false`), `4` (`false`) | `renovacaoPermitidaAbaixoDoLimite` (`PROFESSOR, 2`) e `renovacaoNegadaQuandoLimiteAtingidoOuUltrapassado` (`PROFESSOR, 3, 4`) |

---

### 2.7. `multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)`

**Regra (Anexo A):** Soma a multa de cada empréstimo da lista, cada uma calculada como em `multaCentavos` (com teto individual de R$ 50,00 = `5000` centavos e carência de 3 dias para `PROFESSOR`). O total nunca passa de R$ 100,00 (`10000` centavos). Lista vazia dá total `0`. Lista nula, `tipo` nulo ou qualquer elemento negativo lançam `IllegalArgumentException`.

#### Classes de Equivalência
| ID | Condição de Entrada | Tipo de Classe | Resultado Esperado | Teste que Cobre |
| --- | --- | --- | --- | --- |
| CE-MT-V1 | Lista vazia (`new int[]{}`) para `ALUNO`, `PROFESSOR`, `VISITANTE` | Válida | `0` | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[1]` a `[3]`) |
| CE-MT-V2 | Lista com 1 empréstimo sem atraso ou dentro da carência (`{0}` para `ALUNO`, `{3}` para `PROFESSOR`) | Válida | `0` | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[4]` e `[16]`) |
| CE-MT-V3 | Lista com 1 empréstimo com atraso cobrado (`{1}`, `{2}` para `ALUNO`; `{4}`, `{14}` para `PROFESSOR`) | Válida | Valor individual (`100`, `200`, `100`, `1200`) | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[5], [6], [17], [18]`) |
| CE-MT-V4 | Lista com múltiplos empréstimos cuja soma fica abaixo de `10000` (`{1, 2}`, `{2, 4, 6}`, `{30, 1}`, `{30, 29, 1}`, `{4, 14}`, `{1, 10}`) | Válida | Soma exata das multas individuais | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[7], [8], [9], [10], [19], [21]`) |
| CE-MT-V5 | Lista com múltiplos empréstimos cuja soma atinge ou ultrapassa o teto de `10000` (`{30, 29, 2}`, `{30, 29, 3}`, `{30, 30}`, `{40, 40}`, `{30, 30, 30}`, `{33, 33}`) | Válida | `10000` | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[11]` a `[15]`, `[20]`, `[22]`) |
| CE-MT-I1 | Lista contendo elemento negativo (no único elemento, no fim ou no início: `{-1}`, `{1, -1}`, `{-10, 2}`) | Inválida | `IllegalArgumentException` | `MultaTotalCentavosTest.diasNegativosNoArrayLancaIllegalArgumentException` (`[1]` a `[3]`) |
| CE-MT-I2 | `diasAtrasoPorEmprestimo == null` | Inválida | `IllegalArgumentException` | `MultaTotalCentavosTest.arrayNuloLancaIllegalArgumentException` |
| CE-MT-I3 | `tipo == null` | Inválida | `IllegalArgumentException` | `MultaTotalCentavosTest.tipoNuloLancaIllegalArgumentException` |

#### Valores-Limite
| ID | Fronteira | Valores-Limite Testados | Teste que Cobre |
| --- | --- | --- | --- |
| VL-MT-1 | Tamanho do array (`0` vs `1` elemento) | `new int[]{}` (`0` elementos) e `new int[]{1}` (`1` elemento) | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[1]` a `[5]`) |
| VL-MT-2 | Fronteira de elemento válido vs negativo no array (`-1` vs `0` vs `1`) | `{-1}` (exceção), `{0}` (`0`), `{1}` (`100`) | `diasNegativosNoArrayLancaIllegalArgumentException` (`[1]`) e `calculaMultaTotalCorretamente` (`[4], [5]`) |
| VL-MT-3 | Teto individual de `5000` centavos dentro da soma (`30` vs `40` dias por item) | `{30, 1}` (`5100`), `{30, 30}` (`10000`), `{40, 40}` (`10000`) | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[9], [13], [14]`) |
| VL-MT-4 | Fronteira do teto total de R$ 100,00 (`10000` centavos) | `{30, 29, 1}` -> `9900` (abaixo), `{30, 29, 2}` -> `10000` (no limite), `{30, 29, 3}` -> soma `10100` limitada a `10000` (acima) | `MultaTotalCentavosTest.calculaMultaTotalCorretamente` (`[10], [11], [12]`) |

---

## 3. Resumo da Execução da Suíte

- **Total de casos de teste executados:** 150
- **Testes que passaram:** 83
- **Testes que falharam (revelando defeitos):** 67
- **Erros de execução / ignorados:** 0

Todas as 67 falhas estão listadas individualmente em `DEFEITOS.md`.
