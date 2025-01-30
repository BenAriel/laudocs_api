package br.api.laudocs.laudocs_api.domain.entities;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOrequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "data_consulta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataConsulta;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(nullable = false, length = 100)
    private String medicoSolicitante;

    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Laudo laudo;

    public Consulta(ConsultaDTOrequest dto, Paciente paciente) {
        this.dataConsulta = dto.getDataConsulta();
        this.paciente = paciente;
        this.medicoSolicitante = dto.getMedicoSolicitante();
    }
}
