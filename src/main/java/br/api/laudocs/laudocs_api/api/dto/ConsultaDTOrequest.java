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
public class ConsultaDTOrequest {
    private Long id;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataConsulta;
    private Long pacienteId;
    private String medicoSolicitante;
    private Long laudoId;

    public ConsultaDTOrequest(Consulta consulta) {
        this.id = consulta.getId();
        this.dataConsulta = consulta.getDataConsulta();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoSolicitante = consulta.getMedicoSolicitante();
        this.laudoId = consulta.getLaudo() != null ? consulta.getLaudo().getId() : null;
    }

    public static ConsultaDTOrequest toDTO(Consulta consulta) {
        return new ConsultaDTOrequest(consulta);
    }
}