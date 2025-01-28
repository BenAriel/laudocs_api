package br.api.laudocs.laudocs_api.api.dto;

import br.api.laudocs.laudocs_api.enums.Role;

public record LoginResponseDTO(String token,Role role) {
    public LoginResponseDTO {
    }
}

