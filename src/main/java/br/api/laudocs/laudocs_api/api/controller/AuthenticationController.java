package br.api.laudocs.laudocs_api.api.controller;

import br.api.laudocs.laudocs_api.api.dto.AuthenticationDTO;
import br.api.laudocs.laudocs_api.api.dto.LoginResponseDTO;
import br.api.laudocs.laudocs_api.api.dto.RegisterDTO;
import br.api.laudocs.laudocs_api.config.TokenService;
import br.api.laudocs.laudocs_api.domain.entities.Usuario;
import br.api.laudocs.laudocs_api.domain.repository.UsuarioRepository;
import br.api.laudocs.laudocs_api.exception.ValidationUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

   
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data) {
        ValidationUtils.checkVazio(data.nome(), "Nome não pode ser vazio.");
        ValidationUtils.checkVazio(data.senha(), "Senha não pode ser vazia.");
        ValidationUtils.checkVazio(data.email(), "Email não pode ser vazio.");


        if (this.repository.findByEmail(data.email()).isPresent()) {
            System.out.println("Email já cadastrado");
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        System.out.println("Cadastrando novo usuário");
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.nome(), data.email(), encryptedPassword,data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
