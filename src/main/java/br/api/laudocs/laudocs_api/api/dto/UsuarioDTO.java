package br.api.laudocs.laudocs_api.api.dto;

import br.api.laudocs.laudocs_api.domain.entities.Usuario;
import br.api.laudocs.laudocs_api.enums.Role;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    @Nonnull
    private String email;
    private Role role;

    public UsuarioDTO(Usuario user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public static UsuarioDTO toDTO(Usuario user) {
        return new UsuarioDTO(user.getId(), user.getNome(), user.getEmail(), user.getRole());
    }
}
