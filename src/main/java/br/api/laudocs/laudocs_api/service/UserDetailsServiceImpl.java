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
        System.out.println("loadUserByUsername do UserDetailServiceImp");
        Optional<Usuario> currentUser = repository.findByEmail(email);
        if (currentUser != null) {
            UserDetails user = new org.springframework.security.core
                    .userdetails.User(email, currentUser.get().getSenha(),
                    true, true, true,
                    true, AuthorityUtils.createAuthorityList("USER"));
            return user;
        } else throw new UsernameNotFoundException("Usuário não encontrado!");
    }
}


