package br.api.laudocs.laudocs_api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.api.laudocs.laudocs_api.domain.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    default UserDetails loadUserByEmail(String email) {
        return findByEmail(email).orElse(null);
    }
}
