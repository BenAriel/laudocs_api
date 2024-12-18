package br.api.laudocs.laudocs_api.api.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaudoDTOresend {

    @NotNull(message = "O tipo não pode ser nulo")
    private String type;

    @NotNull(message = "O arquivo não pode ser nulo")
    private MultipartFile file;
}
