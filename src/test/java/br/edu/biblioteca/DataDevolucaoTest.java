package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DataDevolucaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest
    @CsvSource({
        // ALUNO (14 dias): devolução em dia útil (quarta -> quarta)
        "2026-11-04, ALUNO, 2026-11-18",

        // ALUNO (14 dias): resultado cai em sábado (permanece sábado)
        "2026-10-31, ALUNO, 2026-11-14",

        // ALUNO (14 dias): resultado cai em domingo (passa para segunda-feira)
        "2026-11-01, ALUNO, 2026-11-16",

        // PROFESSOR (30 dias): devolução em dia útil (terça-feira)
        "2026-11-01, PROFESSOR, 2026-12-01",

        // PROFESSOR (30 dias): resultado cai em sábado (permanece sábado)
        "2026-11-05, PROFESSOR, 2026-12-05",

        // PROFESSOR (30 dias): resultado cai em domingo (passa para segunda-feira)
        "2026-11-06, PROFESSOR, 2026-12-07",

        // VISITANTE (7 dias): devolução em dia útil (quarta -> quarta)
        "2026-11-04, VISITANTE, 2026-11-11",

        // VISITANTE (7 dias): resultado cai em sábado (permanece sábado)
        "2026-10-31, VISITANTE, 2026-11-07",

        // VISITANTE (7 dias): resultado cai em domingo (passa para segunda-feira)
        "2026-11-01, VISITANTE, 2026-11-09"
    })
    void calculaDataDevolucaoCorretamente(
            LocalDate retirada,
            TipoUsuario tipo,
            LocalDate dataEsperada) {

        assertEquals(
            dataEsperada,
            regras.dataDevolucao(retirada, tipo)
        );
    }

    @Test
    void dataRetiradaNulaLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.dataDevolucao(null, TipoUsuario.ALUNO)
        );
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.dataDevolucao(LocalDate.of(2026, 11, 1), null)
        );
    }
}