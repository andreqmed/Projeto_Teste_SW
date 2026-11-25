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
        // ALUNO: 14 dias, devolução em dia útil
        "2026-11-01, ALUNO, 2026-11-16",

        // PROFESSOR: 30 dias, devolução em dia útil
        "2026-11-01, PROFESSOR, 2026-12-01",

        // VISITANTE: 7 dias, resultado original cai em domingo
        "2026-11-01, VISITANTE, 2026-11-09",

        // ALUNO: resultado original cai em sábado, que deve permanecer
        "2026-10-31, ALUNO, 2026-11-14"
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