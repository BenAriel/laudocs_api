package br.api.laudocs.laudocs_api.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_laudos")
public class Laudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    @JsonBackReference
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long size;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;
}
