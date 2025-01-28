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
public class PacienteDTOupdate {
    private Long id;
    private String nome;

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNasc;

    public PacienteDTOupdate(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.dataNasc = paciente.getDataNasc();
       
    }

    public static PacienteDTOupdate toDTO(Paciente paciente) {
        return new PacienteDTOupdate(paciente.getId(),paciente.getNome(), paciente.getDataNasc());
    }
}
