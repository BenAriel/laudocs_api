package br.api.laudocs.laudocs_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.api.laudocs.laudocs_api.api.dto.UsuarioCreateDTO;
import br.api.laudocs.laudocs_api.api.dto.UsuarioDTO;
import br.api.laudocs.laudocs_api.enums.Role;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String senha;
  
    @Email
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
        this.senha =(dto.getSenha());
        this.role = Role.valueOf(dto.getRole());
    }

     public Usuario(String nome,String email, String senha, Role role){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return senha;
    }
    
    
}
