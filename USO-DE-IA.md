# Uso de Inteligência Artificial — Fase 1 (Caixa-preta)

Conforme exigido no enunciado do projeto (seção **Uso de IA**), este documento descreve o que foi solicitado aos assistentes de IA (ChatGPT e Antigravity/Gemini), o que foi aproveitado e o que precisou ser corrigido pela equipe.

---

## 1. O que foi pedido à IA

1. **Estruturação inicial da suíte JUnit 5 (ChatGPT):**
   - Geração de classes de teste parametrizadas (`@ParameterizedTest` com `@CsvSource`, `@EnumSource` e `@MethodSource`) para os sete métodos de `RegrasBiblioteca` descritos no Anexo A (`prazoDias`, `dataDevolucao`, `limiteEmprestimos`, `multaCentavos`, `podeEmprestar`, `podeRenovar` e `multaTotalCentavos`).
   - Rascunho inicial dos documentos `PLANO-DE-TESTES.md` e `DEFEITOS.md`.
2. **Auditoria de conformidade com o enunciado (Antigravity / Gemini):**
   - Verificação completa das instruções da Fase 1 e do Anexo A (extraídas do PDF do enunciado) contra o código e a documentação existentes no repositório.
   - Revisão dos valores-limite, classes de equivalência, oráculos dos testes e adequação dos arquivos `PLANO-DE-TESTES.md` e `DEFEITOS.md` ao formato exato exigido na tabela de entregas da Fase 1.

---

## 2. O que foi aproveitado

- A organização modular da suíte em sete classes de teste no pacote `br.edu.biblioteca` (`PrazoDiasTest`, `DataDevolucaoTest`, `LimiteEmprestimosTest`, `MultaCentavosTest`, `PodeEmprestarTest`, `PodeRenovarTest` e `MultaTotalCentavosTest`), mantendo também `ExemploTest`.
- O uso de `@ParameterizedTest` do JUnit 5 para testar múltiplas partições e fronteiras sem duplicação excessiva de código.
- A identificação automatizada das 67 falhas na execução contra `lib/biblioteca-1.0.jar` e o mapeamento rastreável entre cada classe de equivalência (`CE-*`), valor-limite (`VL-*`), método de teste e linha de falha em `DEFEITOS.md`.

---

## 3. O que tivemos de corrigir

Durante a revisão e auditoria do material gerado inicialmente com apoio de IA, foram identificados e corrigidos os seguintes problemas:

1. **Oráculo incorreto em `MultaTotalCentavosTest`:**
   - **Problema gerado inicialmente:** O caso `Arguments.of(TipoUsuario.ALUNO, new int[]{2, 4, 6}, 600)` esperava `600` centavos.
   - **Correção:** Pela especificação do Anexo A, para `ALUNO` todos os dias até o 10º custam 100 centavos/dia, portanto `200 + 400 + 600 = 1200` centavos. O valor esperado foi corrigido para `1200` (evitando que o teste falhe contra a versão correta do sistema do professor).

2. **Ausência de teste para entrada inválida (`renovacoesFeitas < 0`) e teste duplicado em `PodeRenovarTest`:**
   - **Problema gerado inicialmente:** O documento `DEFEITOS.md` mencionava que os testes de renovações negativas haviam passado, mas a IA não havia incluído nenhum teste com `renovacoesFeitas < 0` em `PodeRenovarTest.java`. Além disso, havia dois métodos testando exatamente os mesmos pares (`ALUNO, 2` e `PROFESSOR, 3`).
   - **Correção:** Foi removido o método duplicado `renovacaoNegadaQuandoLimiteAtingido` e adicionado o teste parametrizado `renovacoesNegativasLancaIllegalArgumentException` cobrindo `-1` e `-5` para `ALUNO`, `PROFESSOR` e `VISITANTE`.

3. **Lacunas de Análise de Valor-Limite em `MultaCentavosTest`, `MultaTotalCentavosTest`, `PodeEmprestarTest` e `DataDevolucaoTest`:**
   - **Problema gerado inicialmente:** Faltavam pontos imediatamente abaixo dos limites em várias fronteiras (ex.: `9` e `29` dias para `ALUNO`/`VISITANTE` e `12` e `32` dias para `PROFESSOR` em `multaCentavos`; `999` centavos em `podeEmprestar`; soma `9900` e `10100` na fronteira do teto de `10000` em `multaTotalCentavos`; cenários de dia útil, sábado e domingo para todos os três tipos de usuário em `dataDevolucao`).
   - **Correção:** Todos os valores-limite dessas fronteiras foram adicionados às respectivas classes de teste.

4. **Formato de `DEFEITOS.md` e `PLANO-DE-TESTES.md` fora do padrão exigido no enunciado:**
   - **Problema gerado inicialmente:** A primeira versão gerada trazia texto genérico e descritivo, sem listar uma linha por falha em `DEFEITOS.md` e sem mapear explicitamente, para cada método, as classes de equivalência válidas/inválidas, os valores-limite e o teste correspondente em `PLANO-DE-TESTES.md`.
   - **Correção:** Ambos os arquivos foram reescritos em tabelas estruturadas seguindo rigorosamente as instruções da página 2 do enunciado.
