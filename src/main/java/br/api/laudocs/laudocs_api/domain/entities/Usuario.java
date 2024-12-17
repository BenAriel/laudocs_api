package br.api.laudocs.laudocs_api.domain.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.api.laudocs.laudocs_api.api.dto.UsuarioCreateDTO;
import br.api.laudocs.laudocs_api.api.dto.UsuarioDTO;
import br.api.laudocs.laudocs_api.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String senha;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario(UsuarioDTO dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
    }

    public Usuario(UsuarioCreateDTO dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.senha = encodePassword(dto.getSenha());
        this.role = Role.valueOf(dto.getRole());
    }
    
    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
