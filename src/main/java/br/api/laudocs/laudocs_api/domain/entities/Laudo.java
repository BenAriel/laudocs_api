package br.api.laudocs.laudocs_api.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.micrometer.common.lang.NonNull;

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

    @OneToOne(mappedBy = "laudo")
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "paciente_id") 
    private Paciente paciente;

    @NonNull
    private String url;

    @NonNull
    private String contentType;

    @NonNull
    private String type;

    @NonNull
    private Long size;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

}
