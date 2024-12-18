package br.api.laudocs.laudocs_api.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaudoDTOresponse {

    private Long id;

    
    private String type;

    private long size;

    private String url;

    private Long pacienteId; 
    private Long consultaId;

    public LaudoDTOresponse(Laudo laudo) {
        this.id = laudo.getId();
        this.size = laudo.getSize();
        this.type = laudo.getType();
        this.url = laudo.getUrl();

        if (laudo.getPaciente() != null) {
            this.pacienteId = laudo.getPaciente().getId();
        }
        if (laudo.getConsulta() != null) {
            this.consultaId = laudo.getConsulta().getId();
        }
    }
}

