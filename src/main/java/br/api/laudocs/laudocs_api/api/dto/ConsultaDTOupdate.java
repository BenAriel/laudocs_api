package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsultaDTOupdate {
    private Long id;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascPaciente;
    private Long pacienteId;
    private String medicoSolicitante;
    private String nomePaciente;

    public ConsultaDTOupdate(Consulta consulta) {
        this.id = consulta.getId();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoSolicitante = consulta.getMedicoSolicitante();
        this.nomePaciente = consulta.getPaciente().getNome();
        this.dataNascPaciente = consulta.getPaciente().getDataNasc();

    }

    public static ConsultaDTOresponse toDTO(Consulta consulta) {
        return new ConsultaDTOresponse(consulta);
    }
}