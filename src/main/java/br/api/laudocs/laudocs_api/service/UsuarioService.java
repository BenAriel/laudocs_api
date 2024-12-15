package br.api.laudocs.laudocs_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.laudocs.laudocs_api.api.dto.UsuarioCreateDTO;
import br.api.laudocs.laudocs_api.api.dto.UsuarioDTO;
import br.api.laudocs.laudocs_api.domain.entities.Usuario;
import br.api.laudocs.laudocs_api.domain.repository.UsuarioRepository;
import br.api.laudocs.laudocs_api.exception.ValidationException;

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
        checkVazio(usuarioCreateDTO.getNome(), "Nome não pode ser vazio.");
        checkVazio(usuarioCreateDTO.getSenha(), "Senha não pode ser vazia.");

        if (getEmailExistId(usuarioCreateDTO.getEmail()) != null)
            throw new ValidationException("Email já cadastrado no sistema.");

        Usuario usuario = repo.save(new Usuario(usuarioCreateDTO));
        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO updateUser(UsuarioDTO usuarioDTO) {
        var op = repo.findById(usuarioDTO.getId());

        if (!op.isPresent())
            throw new ValidationException("Usuário não existe.");

        checkVazio(usuarioDTO.getNome(), "Novo nome não pode ser vazio.");
        checkVazio(usuarioDTO.getEmail(), "Novo email não pode ser vazio.");

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

    public void checkVazio(String nome, String exception) {
        if (nome == null || nome.equals(""))
            throw new ValidationException(exception);
    }

    public Long getEmailExistId(String email) {
        var op = repo.findByEmail(email);
        if (op.isPresent())
            return op.get().getId();

        return null;
    }
}