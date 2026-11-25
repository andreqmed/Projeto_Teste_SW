package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class PodeRenovarTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // Renovações permitidas abaixo do limite.
    @ParameterizedTest
    @CsvSource({
        "ALUNO, 0",
        "ALUNO, 1",
        "PROFESSOR, 0",
        "PROFESSOR, 1",
        "PROFESSOR, 2"
    })
    void renovacaoPermitidaAbaixoDoLimite(
            TipoUsuario tipo,
            int renovacoesFeitas) {

        assertTrue(
            regras.podeRenovar(tipo, renovacoesFeitas, false, false)
        );
    }

    // Visitantes não podem renovar.
    @Test
    void visitanteNaoPodeRenovar() {
        assertFalse(
            regras.podeRenovar(TipoUsuario.VISITANTE, 0, false, false)
        );
    }

    // Reserva impede renovação.
    @ParameterizedTest
    @EnumSource(TipoUsuario.class)
    void renovacaoNegadaQuandoExisteReserva(TipoUsuario tipo) {
        assertFalse(
            regras.podeRenovar(tipo, 0, true, false)
        );
    }

    // Atraso impede renovação.
    @ParameterizedTest
    @EnumSource(TipoUsuario.class)
    void renovacaoNegadaQuandoEmprestimoEstaEmAtraso(TipoUsuario tipo) {
        assertFalse(
            regras.podeRenovar(tipo, 0, false, true)
        );
    }

    // Limite de renovações atingido ou ultrapassado.
    @ParameterizedTest
    @CsvSource({
        "ALUNO, 2",
        "ALUNO, 3",
        "PROFESSOR, 3",
        "PROFESSOR, 4"
    })
    void renovacaoNegadaQuandoLimiteAtingidoOuUltrapassado(
            TipoUsuario tipo,
            int renovacoesFeitas) {

        assertFalse(
            regras.podeRenovar(tipo, renovacoesFeitas, false, false)
        );
    }

    // Entradas inválidas: número de renovações feitas negativo.
    @ParameterizedTest
    @CsvSource({
        "ALUNO, -1",
        "ALUNO, -5",
        "PROFESSOR, -1",
        "PROFESSOR, -5",
        "VISITANTE, -1",
        "VISITANTE, -5"
    })
    void renovacoesNegativasLancaIllegalArgumentException(
            TipoUsuario tipo,
            int renovacoesFeitas) {

        assertThrows(
            IllegalArgumentException.class,
            () -> regras.podeRenovar(tipo, renovacoesFeitas, false, false)
        );
    }

    @Test
    void tipoNuloLancaIllegalArgumentException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> regras.podeRenovar(null, 0, false, false)
        );
    }
}