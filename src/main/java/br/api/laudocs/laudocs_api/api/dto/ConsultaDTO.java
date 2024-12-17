package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;

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
public class ConsultaDTO {
    private Long id;
    private LocalDate dataConsulta;
    private Long pacienteId;
    private String medicoSolicitante;
    private Long laudoId;

    public ConsultaDTO(Consulta consulta) {
        this.id = consulta.getId();
        this.dataConsulta = consulta.getDataConsulta();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoSolicitante = consulta.getMedicoSolicitante();
        this.laudoId = consulta.getLaudo() != null ? consulta.getLaudo().getId() : null;
    }

    public static ConsultaDTO toDTO(Consulta consulta) {
        return new ConsultaDTO(consulta);
    }
}