package br.api.laudocs.laudocs_api.domain.entities;

import java.time.LocalDate;

import br.api.laudocs.laudocs_api.api.dto.PacienteDTO;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String CPF;

    @Nonnull
    private String nome;

    
    private LocalDate dataNasc;

    private int idade;

    public Paciente(PacienteDTO pacienteDTO) {
        this.nome = pacienteDTO.getNome();
        this.CPF = pacienteDTO.getCpf();
        this.idade = pacienteDTO.getIdade();
        this.dataNasc = pacienteDTO.getDataNasc();
    }
}
