package br.api.laudocs.laudocs_api.api.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {
    private Long id;
    private String nome;
    private String senha;
    @Email
    private String email;
    private String role;
}
