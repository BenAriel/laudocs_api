package br.api.laudocs.laudocs_api.api.dto;

import br.api.laudocs.laudocs_api.enums.Role;

public record RegisterDTO(String nome,String email, String senha, Role role) {
}
