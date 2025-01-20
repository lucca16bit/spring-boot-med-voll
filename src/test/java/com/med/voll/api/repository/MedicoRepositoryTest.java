package com.med.voll.api.repository;

import com.med.voll.api.domain.consulta.Consulta;
import com.med.voll.api.domain.medico.Especialidade;
import com.med.voll.api.domain.medico.Medico;
import com.med.voll.api.domain.paciente.Paciente;
import com.med.voll.api.dto.endereco.DadosEnderecoDto;
import com.med.voll.api.dto.medico.DadosCadastroMedicoDto;
import com.med.voll.api.dto.paciente.DadosCadastroPacienteDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // para nao substituir o banco de dados para o banco de dados em memória
@ActiveProfiles("test") // para usar o application-test
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        // given ou arrange
        var proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)) // para considerar que sempre será na proxima segunda feira
                .toLocalDate().atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAsDez);

        // when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAsDez);

        // then ou assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        // given ou arrange
        var proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)) // para considerar que sempre será na proxima segunda feira
                .toLocalDate().atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        // when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAsDez);

        // then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedicoDto dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedicoDto(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPacienteDto dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPacienteDto(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEnderecoDto dadosEndereco() {
        return new DadosEnderecoDto(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}
