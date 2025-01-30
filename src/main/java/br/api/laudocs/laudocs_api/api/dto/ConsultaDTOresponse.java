package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.enums.Status;
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
public class ConsultaDTOresponse {
    private Long id;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataConsulta;
    private Long pacienteId;
    private String medicoSolicitante;
    private Long laudoId;
    private String nomePaiente;
    private int idadePaciente;
    private LocalDate dataNascPaciente;
    private Status status;

    public ConsultaDTOresponse(Consulta consulta) {
        this.id = consulta.getId();
        this.dataConsulta = consulta.getDataConsulta();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoSolicitante = consulta.getMedicoSolicitante();
        this.laudoId = consulta.getLaudo() != null ? consulta.getLaudo().getId() : null;
        this.nomePaiente = consulta.getPaciente().getNome();
        this.idadePaciente = consulta.getPaciente().getIdade();
        this.dataNascPaciente = consulta.getPaciente().getDataNasc();
        this.status = consulta.getStatus();

    }

    public static ConsultaDTOresponse toDTO(Consulta consulta) {
        return new ConsultaDTOresponse(consulta);
    }
}