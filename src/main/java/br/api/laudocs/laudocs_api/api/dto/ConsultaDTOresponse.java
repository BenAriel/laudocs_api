package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;
import br.api.laudocs.laudocs_api.enums.Status;
import lombok.*;

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
    private List<Long> laudoIds;
    private String nomePaciente;
    private int idadePaciente;
    private LocalDate dataNascPaciente;
    private Status status;

    public ConsultaDTOresponse(Consulta consulta) {
        this.id = consulta.getId();
        this.dataConsulta = consulta.getDataConsulta();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoSolicitante = consulta.getMedicoSolicitante();
        this.laudoIds = consulta.getLaudos() != null ? consulta.getLaudos().stream().map(Laudo::getId).collect(Collectors.toList()) : null; // Corrigido
        this.nomePaciente = consulta.getPaciente().getNome();
        this.idadePaciente = consulta.getPaciente().getIdade();
        this.dataNascPaciente = consulta.getPaciente().getDataNasc();
        this.status = consulta.getStatus();
    }

    public static ConsultaDTOresponse toDTO(Consulta consulta) {
        return new ConsultaDTOresponse(consulta);
    }
}
