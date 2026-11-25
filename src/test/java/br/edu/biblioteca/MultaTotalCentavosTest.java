package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MultaTotalCentavosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    static Stream<Arguments> casosMultaTotal() {
        return Stream.of(
            // Lista vazia
            Arguments.of(TipoUsuario.ALUNO, new int[]{}, 0),
            Arguments.of(TipoUsuario.PROFESSOR, new int[]{}, 0),
            Arguments.of(TipoUsuario.VISITANTE, new int[]{}, 0),

            // Um único empréstimo
            Arguments.of(TipoUsuario.ALUNO, new int[]{0}, 0),
            Arguments.of(TipoUsuario.ALUNO, new int[]{1}, 100),
            Arguments.of(TipoUsuario.ALUNO, new int[]{2}, 200),

            // Soma de múltiplos empréstimos abaixo do teto total (10000)
            Arguments.of(TipoUsuario.ALUNO, new int[]{1, 2}, 300),
            Arguments.of(TipoUsuario.ALUNO, new int[]{2, 4, 6}, 1200),
            Arguments.of(TipoUsuario.ALUNO, new int[]{30, 1}, 5100),

            // Fronteira do teto total (9900, 10000, 10100 -> 10000) e teto individual (5000)
            Arguments.of(TipoUsuario.ALUNO, new int[]{30, 29, 1}, 9900),
            Arguments.of(TipoUsuario.ALUNO, new int[]{30, 29, 2}, 10000),
            Arguments.of(TipoUsuario.ALUNO, new int[]{30, 29, 3}, 10000),
            Arguments.of(TipoUsuario.ALUNO, new int[]{30, 30}, 10000),
            Arguments.of(TipoUsuario.ALUNO, new int[]{40, 40}, 10000),
            Arguments.of(TipoUsuario.ALUNO, new int[]{30, 30, 30}, 10000),

            // Professor (com carência de 3 dias por empréstimo)
            Arguments.of(TipoUsuario.PROFESSOR, new int[]{3}, 0),
            Arguments.of(TipoUsuario.PROFESSOR, new int[]{4}, 100),
            Arguments.of(TipoUsuario.PROFESSOR, new int[]{14}, 1200),
            Arguments.of(TipoUsuario.PROFESSOR, new int[]{4, 14}, 1300),
            Arguments.of(TipoUsuario.PROFESSOR, new int[]{33, 33}, 10000),

            // Visitante
            Arguments.of(TipoUsuario.VISITANTE, new int[]{1, 10}, 1100),
            Arguments.of(TipoUsuario.VISITANTE, new int[]{30, 30}, 10000)
        );
    }

    @ParameterizedTest
    @MethodSource("casosMultaTotal")
    void calculaMultaTotalCorretamente(
            TipoUsuario tipo,
            int[] diasAtrasoPorEmprestimo,
            int multaEsperada) {

        assertEquals(
            multaEsperada,
            regras.multaTotalCentavos(tipo, diasAtrasoPorEmprestimo)
        );
    }

    // Array com elemento negativo.
    @ParameterizedTest
    @MethodSource("arraysComDiasNegativos")
    void diasNegativosNoArrayLancaIllegalArgumentException(
            int[] diasAtrasoPorEmprestimo) {

        assertThrows(
            IllegalArgumentException.class,
            () -> regras.multaTotalCentavos(
                TipoUsuario.ALUNO,
                diasAtrasoPorEmprestimo
            )
        );
    }

    static Stream<Arguments> arraysComDiasNegativos() {
        return Stream.of(
            Arguments.of((Object) new int[]{-1}),
            Arguments.of((Object) new int[]{1, -1}),
            Arguments.of((Object) new int[]{-10, 2})
        );
    }

    @Test
    void arrayNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.multaTotalCentavos(TipoUsuario.ALUNO, null)
        );
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.multaTotalCentavos(null, new int[]{1, 2})
        );
    }
}