package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PodeEmprestarTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Casos em que o empréstimo é permitido.
    @ParameterizedTest
    @CsvSource({
        "ALUNO,  0,    0, false",
        "ALUNO,  1,  500, false",
        "ALUNO,  2,  999, false",
        "ALUNO,  2, 1000, false",
        "PROFESSOR, 0,    0, false",
        "PROFESSOR, 4,  500, false",
        "PROFESSOR, 7,  999, false",
        "PROFESSOR, 7, 1000, false",
        "VISITANTE, 0,    0, false",
        "VISITANTE, 0,  999, false",
        "VISITANTE, 0, 1000, false"
    })
    void emprestimoPermitidoQuandoCondicoesSaoAtendidas(
            TipoUsuario tipo,
            int emprestimosAtivos,
            int multaPendenteCentavos,
            boolean possuiAtraso) {

        assertTrue(
            regras.podeEmprestar(
                tipo,
                emprestimosAtivos,
                multaPendenteCentavos,
                possuiAtraso
            )
        );
    }

    // Casos em que o limite de empréstimos é atingido ou ultrapassado.
    @ParameterizedTest
    @CsvSource({
        "ALUNO, 3",
        "ALUNO, 4",
        "PROFESSOR, 8",
        "PROFESSOR, 9",
        "VISITANTE, 1",
        "VISITANTE, 2"
    })
    void emprestimoNegadoQuandoLimiteDeEmprestimosAtingidoOuUltrapassado(
            TipoUsuario tipo,
            int emprestimosAtivos) {

        assertFalse(
            regras.podeEmprestar(tipo, emprestimosAtivos, 0, false)
        );
    }

    // Multa pendente superior ao limite permitido (valor-limite 1001 e representante acima 1500).
    @ParameterizedTest
    @CsvSource({
        "ALUNO, 1001",
        "ALUNO, 1500",
        "PROFESSOR, 1001",
        "PROFESSOR, 1500",
        "VISITANTE, 1001",
        "VISITANTE, 1500"
    })
    void emprestimoNegadoQuandoMultaSuperaLimite(
            TipoUsuario tipo,
            int multaPendenteCentavos) {

        assertFalse(
            regras.podeEmprestar(tipo, 0, multaPendenteCentavos, false)
        );
    }

    // Empréstimos em atraso impedem novos empréstimos.
    @ParameterizedTest
    @CsvSource({
        "ALUNO",
        "PROFESSOR",
        "VISITANTE"
    })
    void emprestimoNegadoQuandoExisteAtraso(TipoUsuario tipo) {
        assertFalse(
            regras.podeEmprestar(tipo, 0, 0, true)
        );
    }

    // Combinação de restrições.
    @Test
    void emprestimoNegadoQuandoLimiteEAtrasoEstaoPresentes() {
        assertFalse(
            regras.podeEmprestar(TipoUsuario.ALUNO, 3, 0, true)
        );
    }

    // Entradas inválidas.
    @ParameterizedTest
    @CsvSource({
        "ALUNO, -1",
        "PROFESSOR, -1",
        "VISITANTE, -1"
    })
    void quantidadeDeEmprestimosNegativaLancaIllegalArgumentException(
            TipoUsuario tipo,
            int emprestimosAtivos) {

        assertThrows(
            IllegalArgumentException.class,
            () -> regras.podeEmprestar(tipo, emprestimosAtivos, 0, false)
        );
    }

    @ParameterizedTest
    @CsvSource({
        "ALUNO",
        "PROFESSOR",
        "VISITANTE"
    })
    void multaNegativaLancaIllegalArgumentException(TipoUsuario tipo) {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.podeEmprestar(tipo, 0, -1, false)
        );
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.podeEmprestar(null, 0, 0, false)
        );
    }
}