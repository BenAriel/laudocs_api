package br.api.laudocs.laudocs_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.laudocs.laudocs_api.api.dto.UsuarioCreateDTO;
import br.api.laudocs.laudocs_api.api.dto.UsuarioDTO;
import br.api.laudocs.laudocs_api.domain.entities.Usuario;
import br.api.laudocs.laudocs_api.domain.repository.UsuarioRepository;
import br.api.laudocs.laudocs_api.enums.Role;
import br.api.laudocs.laudocs_api.exception.ValidationException;
import br.api.laudocs.laudocs_api.exception.ValidationUtils;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repo;

    public List<UsuarioDTO> getAllUsers() {
        List<Usuario> users = repo.findAll();
        return users.stream().map(UsuarioDTO::toDTO).collect(Collectors.toList());
    }

    public UsuarioDTO getUser(Long userId) {
        var op = repo.findById(userId);

        if (!op.isPresent())
            throw new ValidationException("Usuário não existe.");

        return new UsuarioDTO(op.get());
    }

    public UsuarioDTO createUser(UsuarioCreateDTO usuarioCreateDTO) {
        ValidationUtils.checkVazio(usuarioCreateDTO.getNome(), "Nome não pode ser vazio.");
        ValidationUtils.checkVazio(usuarioCreateDTO.getSenha(), "Senha não pode ser vazia.");
        ValidationUtils.checkVazio(usuarioCreateDTO.getRole(), "O campo 'role' é obrigatório.");

       
        if (getEmailExistId(usuarioCreateDTO.getEmail()) != null)
            throw new ValidationException("Email já cadastrado no sistema.");

    
        Role role;
        try {
            role = Role.valueOf(usuarioCreateDTO.getRole().toUpperCase());
            System.out.println("role: " + role);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("O campo 'role' deve ser 'USER' ou 'ADMIN'.");
        }

        Usuario usuario = new Usuario(usuarioCreateDTO);
        usuario.setRole(role);

        Usuario savedUsuario = repo.save(usuario);
        return new UsuarioDTO(savedUsuario);
    }

    public UsuarioDTO updateUser(UsuarioDTO usuarioDTO) {
        var op = repo.findById(usuarioDTO.getId());

        if (!op.isPresent())
            throw new ValidationException("Usuário não existe.");

        ValidationUtils.checkVazio(usuarioDTO.getNome(), "Novo nome não pode ser vazio.");
        ValidationUtils.checkVazio(usuarioDTO.getEmail(), "Novo email não pode ser vazio.");

        Long idEmail = getEmailExistId(usuarioDTO.getEmail());
        if (idEmail != null && idEmail != usuarioDTO.getId())
            throw new ValidationException("Novo email já cadastrado no sistema.");

        Usuario usuario = op.get();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        Usuario saveUsuario = repo.save(usuario);
        return new UsuarioDTO(saveUsuario);
    }

    public void remove(Long userId) {
        var op = repo.findById(userId);

        if (!op.isPresent())
            throw new ValidationException("Usuário não existe.");

        repo.deleteById(userId);
    }

    public Long getEmailExistId(String email) {
        var op = repo.findByEmail(email);
        if (op.isPresent())
            return op.get().getId();

        return null;
    }
}
