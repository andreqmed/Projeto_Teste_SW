package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PrazoDiasTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest
    @CsvSource({
        "ALUNO, 14",
        "PROFESSOR, 30",
        "VISITANTE, 7"
    })
    void prazoCorretoParaCadaTipoDeUsuario(
            TipoUsuario tipo,
            int prazoEsperado) {

        assertEquals(prazoEsperado, regras.prazoDias(tipo));
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.prazoDias(null)
        );
    }
}