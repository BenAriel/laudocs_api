package br.api.laudocs.laudocs_api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.api.laudocs.laudocs_api.api.dto.UsuarioCreateDTO;
import br.api.laudocs.laudocs_api.api.dto.UsuarioDTO;
import br.api.laudocs.laudocs_api.enums.Role;



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


   public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


  
    
    
}
