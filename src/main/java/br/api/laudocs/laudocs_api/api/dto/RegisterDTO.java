package br.api.laudocs.laudocs_api.api.dto;

import br.api.laudocs.laudocs_api.enums.Role;
import jakarta.validation.constraints.Email;

public record RegisterDTO(String nome, @Email String email, String senha, Role role) {
}
