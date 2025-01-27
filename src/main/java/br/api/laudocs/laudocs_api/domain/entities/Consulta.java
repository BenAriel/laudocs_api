package br.api.laudocs.laudocs_api.domain.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.api.dto.ConsultaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "id_laudo")
    private Laudo laudo;
    
    public Consulta(ConsultaDTO dto, Paciente paciente) {
        this.dataConsulta = dto.getDataConsulta();
        this.paciente = paciente;
        this.medicoSolicitante = dto.getMedicoSolicitante();
    }
}
