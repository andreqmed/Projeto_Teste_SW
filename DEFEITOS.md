# Análise de Defeitos — Sistema de Biblioteca

## 1. Objetivo

Este documento apresenta as divergências identificadas durante a execução dos testes de caixa-preta sobre a biblioteca disponibilizada (`biblioteca-1.0.jar`).

Os comportamentos observados foram comparados com os resultados esperados, estabelecidos a partir das regras funcionais do Anexo A.

As evidências são agrupadas por funcionalidade, de modo que múltiplas falhas de teste relacionadas a um mesmo comportamento não sejam necessariamente contabilizadas como defeitos independentes.

## 2. Resumo da Execução

Na execução realizada em 23/11/2026, foram obtidos os seguintes resultados:

| Resultado         | Quantidade |
| ----------------- | ---------: |
| Testes executados |        111 |
| Falhas            |         52 |
| Erros de execução |          0 |
| Testes ignorados  |          0 |

As falhas indicam divergências entre os resultados esperados e os resultados observados.

## 3. Defeitos e Divergências Observadas

### 3.1. Prazo de empréstimo para VISITANTE

**Funcionalidade:** `prazoDias`

**Comportamento esperado:** O prazo de empréstimo para VISITANTE deve ser de 7 dias.

**Comportamento observado:** O sistema retornou 10 dias.

**Evidência:** O teste parametrizado para VISITANTE falhou, apresentando o valor esperado de 7 dias e o valor observado de 10 dias.

---

### 3.2. Cálculo da data de devolução

**Funcionalidade:** `dataDevolucao`

**Comportamento esperado:** A data de devolução deve ser calculada a partir da data de retirada, considerando o prazo correspondente ao tipo de usuário e a regra de ajuste quando o resultado ocorre em um domingo.

**Comportamento observado:** Foram identificadas divergências em dois cenários:

| Tipo de usuário | Data de retirada | Data esperada | Data observada |
| --------------- | ---------------- | ------------- | -------------- |
| PROFESSOR       | 01/11/2026       | 01/12/2026    | 02/12/2026     |
| VISITANTE       | 01/11/2026       | 09/11/2026    | 12/11/2026     |

**Evidência:** Dois cenários do teste parametrizado `calculaDataDevolucaoCorretamente` falharam.

---

### 3.3. Cálculo da multa por empréstimo

**Funcionalidade:** `multaCentavos`

**Comportamento esperado:** A multa deve respeitar as faixas de cobrança estabelecidas na especificação, o período de carência para PROFESSOR e o teto de 5000 centavos por empréstimo.

**Comportamento observado:** Foram identificadas divergências relacionadas a:

- Valores cobrados nas diferentes faixas de atraso.
- Aplicação do período de carência para PROFESSOR.
- Aplicação do teto de multa por empréstimo.

Além disso, os testes para dias de atraso negativos não identificaram o lançamento da `IllegalArgumentException` esperada.

**Evidências:**

- Casos de cobrança para ALUNO e VISITANTE apresentaram valores divergentes.
- Casos de cobrança para PROFESSOR apresentaram valores divergentes.
- Os testes parametrizados de dias negativos falharam para os três tipos de usuário.

As falhas apontam divergências nas regras de cálculo e validação da multa por empréstimo.

---

### 3.4. Cálculo da multa total

**Funcionalidade:** `multaTotalCentavos`

**Comportamento esperado:** A multa total deve corresponder à soma das multas calculadas individualmente para cada empréstimo, respeitando o teto de 5000 centavos por empréstimo e o teto total de 10000 centavos.

**Comportamento observado:** Foram identificadas divergências nos cálculos das multas individuais utilizadas na composição do total, na soma dos valores e na aplicação dos limites.

Também foram observadas falhas nos testes de validação de dias negativos presentes no array.

**Evidências:**

- Valores esperados e observados divergiram em diferentes combinações de empréstimos.
- Foram identificadas divergências nos casos envolvendo o teto total.
- Os testes com arrays contendo dias negativos não provocaram a exceção esperada.

Essas evidências indicam divergências no cálculo da multa total e na validação das entradas.

---

### 3.5. Regras para concessão de empréstimos

**Funcionalidade:** `podeEmprestar`

**Comportamento esperado:** Um empréstimo deve ser permitido somente quando o usuário possui empréstimos ativos abaixo do limite estabelecido, multa pendente de até 1000 centavos e nenhum empréstimo em atraso.

**Comportamento observado:** Foram identificadas divergências relacionadas às restrições de empréstimos ativos, multas pendentes e atrasos.

Também foram observadas falhas nos testes de validação de multas negativas.

**Evidências:**

- Casos em que o limite de empréstimos foi atingido ou ultrapassado retornaram `true`.
- Casos com multa pendente superior a 1000 centavos retornaram `true`.
- Casos com empréstimos em atraso retornaram `true`.
- Casos com multa negativa não provocaram a exceção esperada.

As evidências demonstram divergências nas regras de concessão de empréstimos e validação das entradas.

---

### 3.6. Regras para renovação de empréstimos

**Funcionalidade:** `podeRenovar`

**Comportamento esperado:** A renovação deve respeitar os limites estabelecidos para ALUNO e PROFESSOR. VISITANTE não pode renovar empréstimos. Reservas e atrasos também devem impedir a renovação.

**Comportamento observado:** Foram identificadas divergências relacionadas ao limite de renovações e à restrição aplicável a VISITANTE.

**Evidências:**

- Um cenário de PROFESSOR no limite de renovações retornou `true`.
- Um cenário de PROFESSOR acima do limite de renovações retornou `true`.
- Um cenário de VISITANTE retornou `true`, contrariando a proibição de renovação.

Os testes de reserva, atraso, renovações negativas e tipo nulo não apresentaram falhas nesta execução.

## 4. Considerações sobre as Falhas

As 52 falhas de teste não representam necessariamente 52 defeitos independentes.

Uma mesma divergência na implementação pode provocar falhas em vários cenários, especialmente quando os testes parametrizados exploram diferentes tipos de usuários ou valores de entrada relacionados à mesma regra de negócio.

Por esse motivo, as evidências foram agrupadas por funcionalidade, evitando atribuir uma quantidade de defeitos independentes sem uma análise que permita distingui-los.

## 5. Conclusão

A execução dos testes identificou divergências em diferentes funcionalidades da biblioteca, incluindo cálculo de prazos, datas de devolução, multas, concessão de empréstimos e renovação.

Os resultados demonstram que o comportamento observado da biblioteca não atende integralmente às regras estabelecidas no Anexo A.

A suíte de testes possibilitou identificar e documentar essas divergências utilizando exclusivamente os resultados esperados da especificação e os comportamentos observados durante a execução.
