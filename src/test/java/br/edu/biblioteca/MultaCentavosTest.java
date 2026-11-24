package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Testes de caixa-preta de multaCentavos(tipo, diasAtraso).
 * Todos os valores esperados vêm da ESPECIFICAÇÃO (Anexo A), nunca do que o sistema devolve.
 *
 * Regras da especificação usadas aqui:
 *  - dias cobrados = diasAtraso (aluno, visitante) ou max(0, diasAtraso - 3) (professor)
 *  - dias cobrados 1..10: R$ 1,00 por dia (100 centavos); do 11º em diante: R$ 2,00 (200 centavos)
 *  - teto de R$ 50,00 (5000 centavos) por empréstimo
 *  - diasAtraso negativo e tipo nulo: IllegalArgumentException
 */
class MultaCentavosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // ---------------------------------------------------------------------
    // Aluno e visitante: todos os dias de atraso são cobrados.
    // Classes: 0 dia | faixa 1 (1..10) | faixa 2 (11..30) | teto (>= 31)
    // Limites: 0, 1, 10, 11, 30, 31
    // ---------------------------------------------------------------------
    @ParameterizedTest(name = "{0} com {1} dias de atraso -> {2} centavos")
    @CsvSource({
        // limite inferior válido (sem atraso)
        "ALUNO,     0,    0",
        "VISITANTE, 0,    0",
        // faixa 1: primeiro dia cobrado
        "ALUNO,     1,  100",
        "VISITANTE, 1,  100",
        // faixa 1: valor interior (partição)
        "ALUNO,     5,  500",
        // faixa 1: último dia a R$ 1,00
        "ALUNO,    10, 1000",
        "VISITANTE,10, 1000",
        // faixa 2: primeiro dia a R$ 2,00
        "ALUNO,    11, 1200",
        "VISITANTE,11, 1200",
        // exemplo da própria especificação: 10 x 1,00 + 2 x 2,00 = 14,00
        "ALUNO,    12, 1400",
        // faixa 2: valor interior
        "ALUNO,    20, 3000",
        // último valor que ainda não bate no teto: 10 x 1,00 + 20 x 2,00 = 50,00
        "ALUNO,    30, 5000",
        "VISITANTE,30, 5000",
        // primeiro valor acima do teto (bruto seria 5200)
        "ALUNO,    31, 5000",
        "VISITANTE,31, 5000",
        // valor interior da classe "teto"
        "ALUNO,   100, 5000",
        "VISITANTE,100,5000",
    })
    void alunoEVisitanteTemTodosOsDiasCobrados(TipoUsuario tipo, int diasAtraso, int esperadoCentavos) {
        assertEquals(esperadoCentavos, regras.multaCentavos(tipo, diasAtraso));
    }

    // ---------------------------------------------------------------------
    // Professor: os 3 primeiros dias de atraso são carência.
    // dias cobrados = diasAtraso - 3, então todos os limites acima deslocam +3.
    // Limites: 0, 3 (último dia de carência), 4 (1º cobrado), 13, 14, 33, 34
    // ---------------------------------------------------------------------
    @ParameterizedTest(name = "PROFESSOR com {0} dias de atraso -> {1} centavos")
    @CsvSource({
        // sem atraso
        " 0,    0",
        // dentro da carência
        " 1,    0",
        // último dia de carência
        " 3,    0",
        // 1º dia cobrado
        " 4,  100",
        // faixa 1: valor interior
        " 8,  500",
        // 10º dia cobrado (último a R$ 1,00)
        "13, 1000",
        // 11º dia cobrado (1º a R$ 2,00)
        "14, 1200",
        // 12 dias cobrados: mesmo valor do exemplo do aluno com 12 dias
        "15, 1400",
        // 30º dia cobrado: exatamente no teto
        "33, 5000",
        // 31º dia cobrado: acima do teto
        "34, 5000",
        // valor interior da classe "teto"
        "200, 5000",
    })
    void professorTemCarenciaDeTresDias(int diasAtraso, int esperadoCentavos) {
        assertEquals(esperadoCentavos, regras.multaCentavos(TipoUsuario.PROFESSOR, diasAtraso));
    }

    // ---------------------------------------------------------------------
    // Classes inválidas
    // ---------------------------------------------------------------------

    // diasAtraso negativo: limite inválido = -1 (o vizinho válido é 0)
    @ParameterizedTest(name = "{0} com -1 dia de atraso lança IllegalArgumentException")
    @EnumSource(TipoUsuario.class)
    void diasAtrasoNegativoNoLimiteLancaExcecao(TipoUsuario tipo) {
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(tipo, -1));
    }

    // diasAtraso negativo: valor interior da classe inválida.
    // Para o professor, -3 .. -1 poderia ser "absorvido" pela carência se a checagem
    // fosse feita depois de descontar os 3 dias; por isso vale testar o tipo PROFESSOR.
    @ParameterizedTest(name = "{0} com -10 dias de atraso lança IllegalArgumentException")
    @EnumSource(TipoUsuario.class)
    void diasAtrasoMuitoNegativoLancaExcecao(TipoUsuario tipo) {
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(tipo, -10));
    }

    @Test
    void tipoNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(null, 5));
    }
}
