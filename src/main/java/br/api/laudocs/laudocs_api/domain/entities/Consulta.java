package br.api.laudocs.laudocs_api.domain.entities;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    
    @NotNull
    private LocalDate dataConsulta;

    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;

   
    private String MedicoSolicitante;

    @JoinColumn(name="id_laudo")
    @NotNull
    private Laudo laudo;
}
