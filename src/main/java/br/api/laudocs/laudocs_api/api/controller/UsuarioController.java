package br.api.laudocs.laudocs_api.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.api.laudocs.laudocs_api.api.dto.UsuarioDTO;
import br.api.laudocs.laudocs_api.service.UsuarioService;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService serv;
    
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(serv.getAllUsers());
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        UsuarioDTO user = serv.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO user = serv.updateUser(usuarioDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/remover/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        serv.remove(userId);
        return ResponseEntity.ok("Usuário removido");
    }
}
