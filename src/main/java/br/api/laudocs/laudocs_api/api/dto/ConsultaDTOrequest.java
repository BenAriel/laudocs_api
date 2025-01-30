package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;
import lombok.*;

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
    private List<Long> laudoIds; // Alterado para suportar múltiplos laudos

    public ConsultaDTOrequest(Consulta consulta) {
        this.id = consulta.getId();
        this.dataConsulta = consulta.getDataConsulta();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoSolicitante = consulta.getMedicoSolicitante();
        this.laudoIds = consulta.getLaudos() != null ? consulta.getLaudos().stream().map(Laudo::getId).collect(Collectors.toList()) : null; // Corrigido
    }

    public static ConsultaDTOrequest toDTO(Consulta consulta) {
        return new ConsultaDTOrequest(consulta);
    }
}
