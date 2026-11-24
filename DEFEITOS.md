# Defeitos encontrados — Fase 1 (caixa-preta)

Uma linha por falha. Esperado vem do Anexo A. O ID da última coluna remete ao `PLANO-DE-TESTES.md`.

| # | Método | Entrada | Esperado | Obtido | Classe ou limite que levou ao teste |
|---|---|---|---|---|---|
| 1 | multaCentavos | (ALUNO, -1) | `IllegalArgumentException` | nenhuma exceção | MC-D2: VL -1, primeiro inválido |
| 2 | multaCentavos | (PROFESSOR, -1) | `IllegalArgumentException` | nenhuma exceção | MC-D2 / MC-P12 |
| 3 | multaCentavos | (VISITANTE, -1) | `IllegalArgumentException` | nenhuma exceção | MC-D2 |
| 4 | multaCentavos | (ALUNO, -10) | `IllegalArgumentException` | nenhuma exceção | MC-D1: PE inválida, negativos |
| 5 | multaCentavos | (PROFESSOR, -10) | `IllegalArgumentException` | nenhuma exceção | MC-D1 / MC-P12 |
| 6 | multaCentavos | (VISITANTE, -10) | `IllegalArgumentException` | nenhuma exceção | MC-D1 |
| 7 | multaCentavos | (ALUNO, 11) | 1200 | 1100 | MC-D7: VL 11, 1º dia a R$ 2,00 |
| 8 | multaCentavos | (VISITANTE, 11) | 1200 | 1100 | MC-D7 |
| 9 | multaCentavos | (ALUNO, 31) | 5000 | 5200 | MC-D11: VL 31, 1º valor acima do teto |
| 10 | multaCentavos | (VISITANTE, 31) | 5000 | 5200 | MC-D11 |
| 11 | multaCentavos | (ALUNO, 100) | 5000 | 19000 | MC-D12: PE teto, interior |
| 12 | multaCentavos | (VISITANTE, 100) | 5000 | 19000 | MC-D12 |
| 13 | multaCentavos | (PROFESSOR, 3) | 0 | 100 | MC-P3: VL 3, último dia de carência |
| 14 | multaCentavos | (PROFESSOR, 4) | 100 | 200 | MC-P4: VL 4, 1º dia cobrado |
| 15 | multaCentavos | (PROFESSOR, 8) | 500 | 600 | MC-P5: PE faixa 1, interior |
| 16 | multaCentavos | (PROFESSOR, 13) | 1000 | 1100 | MC-P6: VL 13, 10º dia cobrado |
| 17 | multaCentavos | (PROFESSOR, 14) | 1200 | 1400 | MC-P7: VL 14, 11º dia cobrado |
| 18 | multaCentavos | (PROFESSOR, 15) | 1400 | 1600 | MC-P8: PE faixa 2 |
| 19 | multaCentavos | (PROFESSOR, 33) | 5000 | 5200 | MC-P9: VL 33, exatamente no teto |
| 20 | multaCentavos | (PROFESSOR, 34) | 5000 | 5400 | MC-P10: VL 34, acima do teto |
| 21 | multaCentavos | (PROFESSOR, 200) | 5000 | 38600 | MC-P11: PE teto, interior |

