package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MultaCentavosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Alunos e visitantes: todos os dias são cobrados.
    @ParameterizedTest
    @CsvSource({
        "ALUNO,  0,    0",
        "ALUNO,  1,  100",
        "ALUNO,  5,  500",
        "ALUNO,  9,  900",
        "ALUNO, 10, 1000",
        "ALUNO, 11, 1200",
        "ALUNO, 12, 1400",
        "ALUNO, 20, 3000",
        "ALUNO, 29, 4800",
        "ALUNO, 30, 5000",
        "ALUNO, 31, 5000",
        "ALUNO, 40, 5000",

        "VISITANTE,  0,    0",
        "VISITANTE,  1,  100",
        "VISITANTE,  5,  500",
        "VISITANTE,  9,  900",
        "VISITANTE, 10, 1000",
        "VISITANTE, 11, 1200",
        "VISITANTE, 12, 1400",
        "VISITANTE, 20, 3000",
        "VISITANTE, 29, 4800",
        "VISITANTE, 30, 5000",
        "VISITANTE, 31, 5000",
        "VISITANTE, 40, 5000"
    })
    void multaCorretaParaAlunoEVisitante(
            TipoUsuario tipo,
            int diasAtraso,
            int multaEsperada) {

        assertEquals(
            multaEsperada,
            regras.multaCentavos(tipo, diasAtraso)
        );
    }

    // Professor: os três primeiros dias são de carência.
    @ParameterizedTest
    @CsvSource({
        " 0,    0",
        " 1,    0",
        " 2,    0",
        " 3,    0",
        " 4,  100",
        " 8,  500",
        "12,  900",
        "13, 1000",
        "14, 1200",
        "15, 1400",
        "23, 3000",
        "32, 4800",
        "33, 5000",
        "34, 5000",
        "40, 5000"
    })
    void multaCorretaParaProfessor(
            int diasAtraso,
            int multaEsperada) {

        assertEquals(
            multaEsperada,
            regras.multaCentavos(TipoUsuario.PROFESSOR, diasAtraso)
        );
    }

    // Valores inválidos: dias negativos.
    @ParameterizedTest
    @CsvSource({
        "ALUNO, -1",
        "ALUNO, -10",
        "PROFESSOR, -1",
        "PROFESSOR, -10",
        "VISITANTE, -1",
        "VISITANTE, -10"
    })
    void diasAtrasoNegativosLancaIllegalArgumentException(
            TipoUsuario tipo,
            int diasAtraso) {

        assertThrows(
            IllegalArgumentException.class,
            () -> regras.multaCentavos(tipo, diasAtraso)
        );
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.multaCentavos(null, 5)
        );
    }
}