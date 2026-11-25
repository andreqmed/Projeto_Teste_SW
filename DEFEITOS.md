# Defeitos Encontrados — Sistema de Biblioteca (Fase 1: Caixa-preta)

Na execução da suíte de 150 testes de caixa-preta contra `lib/biblioteca-1.0.jar`, foram registradas **67 falhas** (e 83 sucessos).

Conforme solicitado no enunciado da Fase 1, a tabela abaixo apresenta **uma linha por falha**, contendo o método testado, a entrada utilizada, o resultado esperado (segundo o Anexo A), o resultado obtido do sistema e a classe de equivalência ou valor-limite que originou o teste.

## 1. Registro Individual de Falhas (Uma Linha por Falha)

| # | Método | Entrada | Resultado Esperado | Resultado Obtido | Classe de Equivalência / Valor-Limite |
| --: | --- | --- | --- | --- | --- |
| 1 | `prazoDias` | `tipo = VISITANTE` | `7` | `10` | `CE-PD-V3` (Prazo para `VISITANTE`) |
| 2 | `dataDevolucao` | `retirada = 2026-11-01, tipo = PROFESSOR` | `2026-12-01` | `2026-12-02` | `CE-DD-V4` (`PROFESSOR`, devolução em dia útil: +30 dias) |
| 3 | `dataDevolucao` | `retirada = 2026-11-06, tipo = PROFESSOR` | `2026-12-07` | `2026-12-06` | `CE-DD-V6` / `VL-DD-1` (`PROFESSOR`, `retirada + 30` cai no domingo `2026-12-06`, deveria ir para segunda `2026-12-07`) |
| 4 | `dataDevolucao` | `retirada = 2026-11-04, tipo = VISITANTE` | `2026-11-11` | `2026-11-14` | `CE-DD-V7` (`VISITANTE`, devolução em dia útil: +7 dias) |
| 5 | `dataDevolucao` | `retirada = 2026-10-31, tipo = VISITANTE` | `2026-11-07` | `2026-11-10` | `CE-DD-V8` / `VL-DD-1` (`VISITANTE`, `retirada + 7` cai no sábado `2026-11-07`) |
| 6 | `dataDevolucao` | `retirada = 2026-11-01, tipo = VISITANTE` | `2026-11-09` | `2026-11-12` | `CE-DD-V9` / `VL-DD-1` (`VISITANTE`, `retirada + 7` cai no domingo `2026-11-08`, passa para segunda `2026-11-09`) |
| 7 | `multaCentavos` | `tipo = ALUNO, diasAtraso = 11` | `1200` | `1100` | `CE-MC-V3` / `VL-MC-3` (Valor-limite: 11º dia cobrado, entrada na 2ª faixa de R$ 2,00/dia) |
| 8 | `multaCentavos` | `tipo = ALUNO, diasAtraso = 31` | `5000` | `5200` | `CE-MC-V4` / `VL-MC-5` (Valor-limite: 31 dias, logo acima do teto de 5000 centavos) |
| 9 | `multaCentavos` | `tipo = ALUNO, diasAtraso = 40` | `5000` | `7000` | `CE-MC-V4` (Acima do teto de 5000 centavos para `ALUNO`) |
| 10 | `multaCentavos` | `tipo = VISITANTE, diasAtraso = 11` | `1200` | `1100` | `CE-MC-V3` / `VL-MC-3` (Valor-limite: 11º dia cobrado para `VISITANTE`, entrada na 2ª faixa) |
| 11 | `multaCentavos` | `tipo = VISITANTE, diasAtraso = 31` | `5000` | `5200` | `CE-MC-V4` / `VL-MC-5` (Valor-limite: 31 dias para `VISITANTE`, logo acima do teto de 5000 centavos) |
| 12 | `multaCentavos` | `tipo = VISITANTE, diasAtraso = 40` | `5000` | `7000` | `CE-MC-V4` (Acima do teto de 5000 centavos para `VISITANTE`) |
| 13 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 3` | `0` | `100` | `CE-MC-V5` / `VL-MC-2` (Valor-limite: 3º dia de carência de `PROFESSOR`) |
| 14 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 4` | `100` | `200` | `CE-MC-V6` / `VL-MC-2` (Valor-limite: 4º dia de atraso de `PROFESSOR`, 1º dia cobrado) |
| 15 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 8` | `500` | `600` | `CE-MC-V6` (1ª faixa de cobrança para `PROFESSOR`: 5 dias cobrados) |
| 16 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 12` | `900` | `1000` | `CE-MC-V6` / `VL-MC-4` (Valor-limite: 9º dia cobrado de `PROFESSOR`) |
| 17 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 13` | `1000` | `1100` | `CE-MC-V6` / `VL-MC-4` (Valor-limite: 10º dia cobrado de `PROFESSOR`, fim da 1ª faixa) |
| 18 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 14` | `1200` | `1400` | `CE-MC-V7` / `VL-MC-4` (Valor-limite: 11º dia cobrado de `PROFESSOR`, início da 2ª faixa) |
| 19 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 15` | `1400` | `1600` | `CE-MC-V7` / `VL-MC-4` (Valor-limite: 12º dia cobrado de `PROFESSOR`) |
| 20 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 23` | `3000` | `3200` | `CE-MC-V7` (2ª faixa de cobrança para `PROFESSOR`: 20 dias cobrados) |
| 21 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 32` | `4800` | `5000` | `CE-MC-V7` / `VL-MC-6` (Valor-limite: 29º dia cobrado de `PROFESSOR`, logo abaixo do teto) |
| 22 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 33` | `5000` | `5200` | `CE-MC-V8` / `VL-MC-6` (Valor-limite: 30º dia cobrado de `PROFESSOR`, exato teto de 5000) |
| 23 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 34` | `5000` | `5400` | `CE-MC-V8` / `VL-MC-6` (Valor-limite: 31º dia cobrado de `PROFESSOR`, logo acima do teto) |
| 24 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = 40` | `5000` | `6600` | `CE-MC-V8` (Acima do teto de 5000 centavos para `PROFESSOR`) |
| 25 | `multaCentavos` | `tipo = ALUNO, diasAtraso = -1` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MC-I1` / `VL-MC-1` (Valor-limite inválido: `diasAtraso = -1` para `ALUNO`) |
| 26 | `multaCentavos` | `tipo = ALUNO, diasAtraso = -10` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MC-I1` (Classe inválida: `diasAtraso < 0` para `ALUNO`) |
| 27 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = -1` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MC-I1` / `VL-MC-1` (Valor-limite inválido: `diasAtraso = -1` para `PROFESSOR`) |
| 28 | `multaCentavos` | `tipo = PROFESSOR, diasAtraso = -10` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MC-I1` (Classe inválida: `diasAtraso < 0` para `PROFESSOR`) |
| 29 | `multaCentavos` | `tipo = VISITANTE, diasAtraso = -1` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MC-I1` / `VL-MC-1` (Valor-limite inválido: `diasAtraso = -1` para `VISITANTE`) |
| 30 | `multaCentavos` | `tipo = VISITANTE, diasAtraso = -10` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MC-I1` (Classe inválida: `diasAtraso < 0` para `VISITANTE`) |
| 31 | `podeEmprestar` | `tipo = ALUNO, ativos = 3, multa = 0, atraso = false` | `false` | `true` | `CE-PE-V4` / `VL-PE-2` (Valor-limite: `emprestimosAtivos == 3`, limite atingido para `ALUNO`) |
| 32 | `podeEmprestar` | `tipo = PROFESSOR, ativos = 8, multa = 0, atraso = false` | `false` | `true` | `CE-PE-V4` / `VL-PE-3` (Valor-limite: `emprestimosAtivos == 8`, limite atingido para `PROFESSOR`) |
| 33 | `podeEmprestar` | `tipo = VISITANTE, ativos = 1, multa = 0, atraso = false` | `false` | `true` | `CE-PE-V4` / `VL-PE-4` (Valor-limite: `emprestimosAtivos == 1`, limite atingido para `VISITANTE`) |
| 34 | `podeEmprestar` | `tipo = ALUNO, ativos = 0, multa = 1001, atraso = false` | `false` | `true` | `CE-PE-V5` / `VL-PE-6` (Valor-limite: `multaPendenteCentavos = 1001`, logo acima de 1000 para `ALUNO`) |
| 35 | `podeEmprestar` | `tipo = ALUNO, ativos = 0, multa = 1500, atraso = false` | `false` | `true` | `CE-PE-V5` (Classe válida: `multaPendenteCentavos > 1000` para `ALUNO`) |
| 36 | `podeEmprestar` | `tipo = PROFESSOR, ativos = 0, multa = 1001, atraso = false` | `false` | `true` | `CE-PE-V5` / `VL-PE-6` (Valor-limite: `multaPendenteCentavos = 1001`, logo acima de 1000 para `PROFESSOR`) |
| 37 | `podeEmprestar` | `tipo = PROFESSOR, ativos = 0, multa = 1500, atraso = false` | `false` | `true` | `CE-PE-V5` (Classe válida: `multaPendenteCentavos > 1000` para `PROFESSOR`) |
| 38 | `podeEmprestar` | `tipo = VISITANTE, ativos = 0, multa = 1001, atraso = false` | `false` | `true` | `CE-PE-V5` / `VL-PE-6` (Valor-limite: `multaPendenteCentavos = 1001`, logo acima de 1000 para `VISITANTE`) |
| 39 | `podeEmprestar` | `tipo = VISITANTE, ativos = 0, multa = 1500, atraso = false` | `false` | `true` | `CE-PE-V5` (Classe válida: `multaPendenteCentavos > 1000` para `VISITANTE`) |
| 40 | `podeEmprestar` | `tipo = ALUNO, ativos = 0, multa = 0, atraso = true` | `false` | `true` | `CE-PE-V6` (`possuiAtraso == true` impede empréstimo para `ALUNO`) |
| 41 | `podeEmprestar` | `tipo = PROFESSOR, ativos = 0, multa = 0, atraso = true` | `false` | `true` | `CE-PE-V6` (`possuiAtraso == true` impede empréstimo para `PROFESSOR`) |
| 42 | `podeEmprestar` | `tipo = VISITANTE, ativos = 0, multa = 0, atraso = true` | `false` | `true` | `CE-PE-V6` (`possuiAtraso == true` impede empréstimo para `VISITANTE`) |
| 43 | `podeEmprestar` | `tipo = ALUNO, ativos = 3, multa = 0, atraso = true` | `false` | `true` | `CE-PE-V4` / `CE-PE-V6` (Combinação: limite de empréstimos atingido + atraso) |
| 44 | `podeEmprestar` | `tipo = ALUNO, ativos = 0, multa = -1, atraso = false` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-PE-I2` / `VL-PE-5` (Valor-limite inválido: `multaPendenteCentavos = -1` para `ALUNO`) |
| 45 | `podeEmprestar` | `tipo = PROFESSOR, ativos = 0, multa = -1, atraso = false` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-PE-I2` / `VL-PE-5` (Valor-limite inválido: `multaPendenteCentavos = -1` para `PROFESSOR`) |
| 46 | `podeEmprestar` | `tipo = VISITANTE, ativos = 0, multa = -1, atraso = false` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-PE-I2` / `VL-PE-5` (Valor-limite inválido: `multaPendenteCentavos = -1` para `VISITANTE`) |
| 47 | `podeRenovar` | `tipo = VISITANTE, renovacoes = 0, reserva = false, atraso = false` | `false` | `true` | `CE-PR-V3` / `VL-PR-1` (`VISITANTE` nunca pode renovar) |
| 48 | `podeRenovar` | `tipo = PROFESSOR, renovacoes = 3, reserva = false, atraso = false` | `false` | `true` | `CE-PR-V4` / `VL-PR-3` (Valor-limite: `renovacoesFeitas == 3`, limite atingido para `PROFESSOR`) |
| 49 | `multaTotalCentavos` | `tipo = ALUNO, dias = [1]` | `100` | `0` | `CE-MT-V3` / `VL-MT-1` / `VL-MT-2` (1 empréstimo com 1 dia de atraso para `ALUNO`) |
| 50 | `multaTotalCentavos` | `tipo = ALUNO, dias = [2]` | `200` | `0` | `CE-MT-V3` (1 empréstimo com 2 dias de atraso para `ALUNO`) |
| 51 | `multaTotalCentavos` | `tipo = ALUNO, dias = [1, 2]` | `300` | `200` | `CE-MT-V4` (Soma de 2 empréstimos para `ALUNO`: 100 + 200) |
| 52 | `multaTotalCentavos` | `tipo = ALUNO, dias = [2, 4, 6]` | `1200` | `1000` | `CE-MT-V4` (Soma de 3 empréstimos para `ALUNO`: 200 + 400 + 600) |
| 53 | `multaTotalCentavos` | `tipo = ALUNO, dias = [30, 1]` | `5100` | `100` | `CE-MT-V4` / `VL-MT-3` (1º empréstimo no teto individual de 5000 + 2º empréstimo de 100) |
| 54 | `multaTotalCentavos` | `tipo = ALUNO, dias = [30, 29, 1]` | `9900` | `4900` | `CE-MT-V4` / `VL-MT-4` (Valor-limite: soma `9900`, imediatamente abaixo do teto total de `10000`) |
| 55 | `multaTotalCentavos` | `tipo = ALUNO, dias = [30, 29, 2]` | `10000` | `5000` | `CE-MT-V5` / `VL-MT-4` (Valor-limite: soma `10000`, exatamente no teto total) |
| 56 | `multaTotalCentavos` | `tipo = ALUNO, dias = [30, 29, 3]` | `10000` | `5100` | `CE-MT-V5` / `VL-MT-4` (Valor-limite: soma bruta `10100`, imediatamente acima do teto total de `10000`) |
| 57 | `multaTotalCentavos` | `tipo = ALUNO, dias = [30, 30]` | `10000` | `5000` | `CE-MT-V5` / `VL-MT-3` (Dois empréstimos no teto individual: 5000 + 5000) |
| 58 | `multaTotalCentavos` | `tipo = ALUNO, dias = [40, 40]` | `10000` | `7000` | `CE-MT-V5` / `VL-MT-3` (Dois empréstimos acima do teto individual: 5000 + 5000) |
| 59 | `multaTotalCentavos` | `tipo = PROFESSOR, dias = [4]` | `100` | `0` | `CE-MT-V3` (1 empréstimo de `PROFESSOR` com 4 dias -> 1 dia cobrado) |
| 60 | `multaTotalCentavos` | `tipo = PROFESSOR, dias = [14]` | `1200` | `0` | `CE-MT-V3` (1 empréstimo de `PROFESSOR` com 14 dias -> 11 dias cobrados) |
| 61 | `multaTotalCentavos` | `tipo = PROFESSOR, dias = [4, 14]` | `1300` | `1400` | `CE-MT-V4` (2 empréstimos de `PROFESSOR`: 100 + 1200) |
| 62 | `multaTotalCentavos` | `tipo = PROFESSOR, dias = [33, 33]` | `10000` | `5200` | `CE-MT-V5` (2 empréstimos de `PROFESSOR` no teto individual: 5000 + 5000) |
| 63 | `multaTotalCentavos` | `tipo = VISITANTE, dias = [1, 10]` | `1100` | `1000` | `CE-MT-V4` (2 empréstimos de `VISITANTE`: 100 + 1000) |
| 64 | `multaTotalCentavos` | `tipo = VISITANTE, dias = [30, 30]` | `10000` | `5000` | `CE-MT-V5` (2 empréstimos de `VISITANTE` no teto individual: 5000 + 5000) |
| 65 | `multaTotalCentavos` | `tipo = ALUNO, dias = [-1]` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MT-I1` / `VL-MT-2` (Valor-limite inválido: único elemento negativo `-1` no array) |
| 66 | `multaTotalCentavos` | `tipo = ALUNO, dias = [1, -1]` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MT-I1` (Classe inválida: elemento negativo a partir da 2ª posição do array) |
| 67 | `multaTotalCentavos` | `tipo = ALUNO, dias = [-10, 2]` | `IllegalArgumentException` | Nenhuma exceção lançada | `CE-MT-I1` (Classe inválida: elemento negativo na 1ª posição do array) |

---

## 2. Síntese dos Defeitos Revelados pelas Falhas

Embora a tabela acima liste cada uma das 67 falhas individualmente, a análise de caixa-preta dos resultados obtidos permite agrupar as causas aparentes por método:

1. **`prazoDias`**: Retorna `10` para `VISITANTE` em vez de `7` (falha #1).
2. **`dataDevolucao`**:
   - Para `VISITANTE`, soma 10 dias em vez de 7 (reflexo direto do defeito de `prazoDias`, falhas #4 a #6).
   - Para `PROFESSOR`, a contagem/ajuste de dias diverge em 1 dia (`2026-11-01 + 30 dias` retornou `2026-12-02` em vez de `2026-12-01`, e `2026-11-06 + 30 dias` permaneceu no domingo `2026-12-06` em vez de avançar para segunda-feira `2026-12-07`, falhas #2 e #3).
3. **`multaCentavos`**:
   - **Carência do `PROFESSOR`:** O 3º dia já está sendo cobrado (`diasAtraso = 3` retorna `100` em vez de `0`), indicando carência de apenas 2 dias em vez de 3 (falhas #13 a #24).
   - **Fronteira da 2ª faixa (`ALUNO`/`VISITANTE`):** Com 11 dias de atraso, retorna `1100` em vez de `1200` (o 11º dia foi cobrado a R$ 1,00 em vez de R$ 2,00, falhas #7 e #10).
   - **Teto de R$ 50,00 (`5000` centavos):** Valores acima de 30 dias cobrados continuam crescendo além de `5000` (`5200`, `5400`, `6600`, `7000`), indicando ausência de teto por empréstimo (falhas #8, #9, #11, #12, #22 a #24).
   - **Validação de negativos:** Não lança `IllegalArgumentException` quando `diasAtraso < 0` (falhas #25 a #30).
4. **`podeEmprestar`**:
   - Permite emprestar quando `emprestimosAtivos == limite` (`3` para `ALUNO`, `8` para `PROFESSOR`, `1` para `VISITANTE`), indicando uso de `<=` em vez de `<` (falhas #31 a #33).
   - Ignora o limite de `multaPendenteCentavos <= 1000` e a flag `possuiAtraso == true`, retornando `true` indevidamente (falhas #34 a #43).
   - Não lança `IllegalArgumentException` para `multaPendenteCentavos < 0` (falhas #44 a #46).
5. **`podeRenovar`**:
   - Permite renovação para `VISITANTE` (falha #47).
   - Permite renovação para `PROFESSOR` quando `renovacoesFeitas == 3` (uso de `<= 3` em vez de `< 3`, falha #48).
6. **`multaTotalCentavos`**:
   - Ignora o primeiro elemento do array (`dias[0]`): arrays de 1 elemento (`[1]`, `[2]`, `[4]`, `[14]`) retornam `0`, e arrays com mais elementos somam apenas do índice 1 em diante (ex.: `[30, 1]` retorna `100`, `[1, 2]` retorna `200`, falhas #49 a #64).
   - Não lança `IllegalArgumentException` quando há elementos negativos no array (falhas #65 a #67).
