package br.api.laudocs.laudocs_api.domain.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.laudocs.laudocs_api.api.dto.PacienteDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_pacientes")
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;

    private String nome;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_nasc")
    private LocalDate dataNasc;
   
    private int idade;

    @OneToMany(mappedBy = "paciente")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Laudo> laudos;

    public Paciente(PacienteDTO dto) {
        this.cpf = dto.getCpf();
        this.nome = dto.getNome();
        this.dataNasc = dto.getDataNasc();
        this.idade = dto.getIdade();
    }
}
