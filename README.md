# Projeto de Teste de Software — Sistema de Biblioteca (Fase 1: Caixa-preta)

Este repositório contém a suíte de testes de caixa-preta (JUnit 5) e a documentação do sistema de biblioteca compilado (`lib/biblioteca-1.0.jar`).

## Equipe
- Diego Amorim Vidal
- André Queiroz Medeiros
- Antonio Carlos Ferreira de Souza
---

## 1. Pré-requisitos

- **Java (JDK):** versão 17 ou superior.
- **Apache Maven:** versão 3.8+ (opcional caso execute diretamente pela IDE, pois IntelliJ IDEA, VS Code e Eclipse já possuem suporte embutido a Maven e JUnit 5).

---

## 2. Estrutura do Projeto

- `lib/biblioteca-1.0.jar`: Biblioteca compilada sob teste (SUT).
- `src/test/java/br/edu/biblioteca/`: Suíte de testes JUnit 5 organizada por método especificado no Anexo A:
  - `PrazoDiasTest.java`
  - `DataDevolucaoTest.java`
  - `LimiteEmprestimosTest.java`
  - `MultaCentavosTest.java`
  - `PodeEmprestarTest.java`
  - `PodeRenovarTest.java`
  - `MultaTotalCentavosTest.java`
  - `ExemploTest.java`
- `PLANO-DE-TESTES.md`: Classes de equivalência (válidas e inválidas), valores-limite e rastreabilidade dos testes por método.
- `DEFEITOS.md`: Registro individual de todas as falhas encontradas na execução e síntese dos defeitos identificados.
- `USO-DE-IA.md`: Relato do uso de ferramentas de Inteligência Artificial, aproveitamento e correções realizadas.

---

## 3. Como Executar os Testes
### Opção A: Via Linha de Comando (Maven)

Na raiz do projeto (onde está localizado o arquivo `pom.xml`), execute:

```bash
mvn test
```

Para executar apenas uma classe de teste específica:

```bash
mvn test -Dtest=MultaCentavosTest
```

> [!NOTE]
> O arquivo `pom.xml` está configurado com `<testFailureIgnore>true</testFailureIgnore>` no `maven-surefire-plugin`. Por isso, o comando `mvn test` finalizará com `BUILD SUCCESS` mesmo havendo falhas nos testes, pois as falhas são esperadas e revelam os defeitos existentes em `biblioteca-1.0.jar`.
> Os relatórios detalhados da execução ficam disponíveis no diretório `target/surefire-reports/`.

---

## 4. Resultado Esperado da Execução

Ao executar a suíte completa contra `lib/biblioteca-1.0.jar`, devem ser contabilizados:

- **Total de testes executados:** 150
- **Testes aprovados:** 83
- **Testes com falha (defeitos revelados):** 67
- **Erros / Ignorados:** 0
