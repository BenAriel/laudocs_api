package br.api.laudocs.laudocs_api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.api.laudocs.laudocs_api.api.dto.PacienteDTO;
import br.api.laudocs.laudocs_api.service.PacienteService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/paciente")
public class PacienteController {
    @Autowired
    PacienteService serv;

    @GetMapping
    public ResponseEntity<?> getAllPacientes() {
        return ResponseEntity.ok(serv.getAllPacientes());
    }

    @GetMapping("/{pacienteId}")
    public ResponseEntity<?> getPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(serv.getPaciente(pacienteId));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getPacienteByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(serv.getPacienteByCpf(cpf));
    }

    @PostMapping("/criar")
    public ResponseEntity<?> createPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(serv.createPaciente(pacienteDTO));
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> updatePaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(serv.updatePaciente(pacienteDTO));
    }

    @DeleteMapping("/remover/{pacienteId}")
    public ResponseEntity<?> removePaciente(@PathVariable Long pacienteId) {
        serv.removePaciente(pacienteId);
        return ResponseEntity.ok("Paciente removido.");
    }
}
