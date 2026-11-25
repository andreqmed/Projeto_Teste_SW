# Plano de Testes — Sistema de Biblioteca

## 1. Objetivo

Este trabalho tem como objetivo avaliar o comportamento do sistema de biblioteca por meio de testes de caixa-preta, verificando se as funcionalidades implementadas atendem às regras estabelecidas na especificação (Anexo A).

Os testes são realizados sobre a biblioteca compilada disponibilizada (`biblioteca-1.0.jar`), sem acesso ou alteração de seu código-fonte.

O objetivo é identificar divergências entre os resultados esperados, definidos pela especificação, e os resultados efetivamente retornados pelo sistema.

## 2. Estratégia de Testes

Foi adotada a abordagem de testes de caixa-preta, na qual os testes são projetados com base nas entradas, saídas esperadas e regras funcionais especificadas, sem conhecimento ou dependência da implementação interna do sistema.

A especificação do Anexo A foi utilizada como oráculo para determinar os resultados esperados.

Os testes foram implementados utilizando JUnit 5, organizados em classes de acordo com as funcionalidades da biblioteca.

## 3. Técnicas de Projeto de Testes Utilizadas

### 3.1. Particionamento de Equivalência

As entradas foram agrupadas em classes de equivalência, considerando situações nas quais se espera que o sistema apresente comportamentos semelhantes.

Foram consideradas, entre outras, as seguintes categorias:

- Tipos de usuário: ALUNO, PROFESSOR e VISITANTE.
- Quantidades válidas e inválidas de empréstimos e renovações.
- Quantidades de dias de atraso em diferentes faixas de cobrança.
- Valores de multas pendentes dentro e fora do limite permitido.
- Situações com e sem atrasos ou reservas.

A utilização dessas classes permite selecionar casos representativos sem precisar testar todas as combinações possíveis de entradas.

### 3.2. Análise de Valores Limite

Foram selecionados casos próximos aos limites estabelecidos nas regras de negócio, especialmente nas situações em que o comportamento do sistema muda.

Entre os limites explorados estão:

- 10 e 11 dias de atraso, considerando a mudança na tarifa diária.
- 3 e 4 dias de atraso para PROFESSOR, considerando o período de carência.
- Valores próximos ao limite de multa por empréstimo, de 5000 centavos.
- Valores próximos ao limite de multa pendente para concessão de empréstimos, de 1000 centavos.
- Quantidades de empréstimos próximas aos limites de cada tipo de usuário.
- Quantidades de renovações próximas aos limites de cada tipo de usuário.
- Valores próximos ao limite de multa total, de 10000 centavos.

Também foram considerados valores acima dos limites, permitindo verificar se as restrições são aplicadas corretamente.

### 3.3. Testes de Entradas Inválidas

Foram incluídos testes para entradas inválidas, verificando se o sistema lança `IllegalArgumentException` nas situações previstas pela especificação.

Entre essas situações estão:

- Tipos de usuário nulos.
- Datas de retirada nulas.
- Quantidades negativas de empréstimos e renovações.
- Dias de atraso negativos.
- Arrays nulos ou contendo valores negativos, quando aplicável.

## 4. Organização dos Testes

Os testes foram organizados em sete classes, cada uma associada a uma funcionalidade da biblioteca.

| Classe                   | Funcionalidade       | Principais cenários                                                                    |
| ------------------------ | -------------------- | -------------------------------------------------------------------------------------- |
| `PrazoDiasTest`          | `prazoDias`          | Prazo para cada tipo de usuário e tipo nulo.                                           |
| `DataDevolucaoTest`      | `dataDevolucao`      | Cálculo da data de devolução, ajustes para domingo e entradas nulas.                   |
| `LimiteEmprestimosTest`  | `limiteEmprestimos`  | Limite de empréstimos por tipo de usuário e tipo nulo.                                 |
| `MultaCentavosTest`      | `multaCentavos`      | Faixas de cobrança, carência de professor, teto por empréstimo e entradas inválidas.   |
| `PodeEmprestarTest`      | `podeEmprestar`      | Limite de empréstimos, multa pendente, atrasos e entradas inválidas.                   |
| `PodeRenovarTest`        | `podeRenovar`        | Limites de renovação, reservas, atrasos, restrição de visitante e entradas inválidas.  |
| `MultaTotalCentavosTest` | `multaTotalCentavos` | Soma de multas, teto total, carência de professor, arrays vazios e entradas inválidas. |

## 5. Execução dos Testes

Os testes podem ser executados utilizando Maven, a partir da raiz do projeto, com o comando:

```bash
mvn test
```

O framework JUnit 5 foi utilizado para implementar testes unitários e parametrizados, permitindo executar diferentes cenários a partir de conjuntos de dados.

## 6. Resultados da Execução

Na execução realizada em 23/11/2026, foram obtidos os seguintes resultados:

| Resultado         | Quantidade |
| ----------------- | ---------: |
| Testes executados |        111 |
| Testes com falha  |         52 |
| Testes sem falha  |         59 |
| Erros de execução |          0 |
| Testes ignorados  |          0 |

As falhas identificadas indicam divergências entre os comportamentos observados na biblioteca e os comportamentos esperados definidos na especificação.

As evidências obtidas durante a execução serão analisadas e documentadas separadamente no arquivo `DEFEITOS.md`.

O resultado `BUILD SUCCESS` apresentado pelo Maven não significa que todos os testes passaram. A execução foi concluída, porém o relatório do Surefire indicou falhas nas asserções.

## 7. Considerações

A suíte de testes foi projetada para verificar as regras funcionais da biblioteca, explorando diferentes tipos de usuários, valores limites, combinações de condições e entradas inválidas.

A organização dos testes por funcionalidade facilita a identificação dos comportamentos divergentes e a manutenção da suíte.

Os resultados obtidos serão utilizados para documentar os defeitos observados, sem assumir que cada falha de teste representa necessariamente um defeito independente.
