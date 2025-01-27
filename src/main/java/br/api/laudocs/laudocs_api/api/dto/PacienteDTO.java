package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.domain.entities.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    private String cpf;
    private String nome;

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNasc;
    
    private int idade;

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.cpf = paciente.getCpf();
        this.nome = paciente.getNome();
        this.dataNasc = paciente.getDataNasc();
        this.idade = paciente.getIdade();
    }

    public static PacienteDTO toDTO(Paciente paciente) {
        return new PacienteDTO(paciente.getId(), paciente.getCpf(), paciente.getNome(), paciente.getDataNasc(), paciente.getIdade());
    }
}
