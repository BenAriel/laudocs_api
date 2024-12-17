package br.api.laudocs.laudocs_api.service;
import br.api.laudocs.laudocs_api.domain.entities.Usuario;
import br.api.laudocs.laudocs_api.domain.repository.UsuarioRepository;

import java.util.Optional;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    private final UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Usuario> currentUser = repository.findByEmail(email);
    if (currentUser.isPresent()) {
        Usuario usuario = currentUser.get();
        System.out.println("role do usuario: " + usuario.getRole().name());
        System.out.println("lista de roles: " + AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                AuthorityUtils.createAuthorityList(usuario.getRole().name())
        );
    } else {
        throw new UsernameNotFoundException("Usuário não encontrado!");
    }
}
}


