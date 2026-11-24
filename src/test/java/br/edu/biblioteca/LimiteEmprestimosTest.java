package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LimiteEmprestimosTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @ParameterizedTest
    @CsvSource({
        "ALUNO, 3",
        "PROFESSOR, 8",
        "VISITANTE, 1"
    })
    void limiteCorretoParaCadaTipoDeUsuario(
            TipoUsuario tipo,
            int limiteEsperado) {

        assertEquals(limiteEsperado, regras.limiteEmprestimos(tipo));
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.limiteEmprestimos(null)
        );
    }
}