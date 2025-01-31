package br.api.laudocs.laudocs_api.api.controller;

import br.api.laudocs.laudocs_api.api.dto.AuthenticationDTO;
import br.api.laudocs.laudocs_api.api.dto.LoginResponseDTO;
import br.api.laudocs.laudocs_api.api.dto.RegisterDTO;
import br.api.laudocs.laudocs_api.domain.entities.Usuario;
import br.api.laudocs.laudocs_api.domain.repository.UsuarioRepository;
import br.api.laudocs.laudocs_api.exception.ValidationUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})

public class AuthenticationController {

    @Autowired
    private UsuarioRepository repository;



   
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        
        
        System.out.println("email: " + data.email());
        Usuario user = this.repository.findByEmail(data.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String senha = data.senha();
        System.out.println("senha vinda do front: " + senha);
        System.out.println("senha do banco: " + user.getSenha());
        if (senha == null || !senha.equals(user.getSenha())) {
            System.out.println("Senha incorreta");
            return ResponseEntity.badRequest().build();
        }
    

        return ResponseEntity.ok(new LoginResponseDTO(user.getRole()));
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

        Usuario newUser = new Usuario(data.nome(), data.email(), data.senha(),data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
