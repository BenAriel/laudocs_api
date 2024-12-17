package br.api.laudocs.laudocs_api.api.controller;

import java.util.List;

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

import br.api.laudocs.laudocs_api.api.dto.LaudoDTO;
import br.api.laudocs.laudocs_api.service.LaudoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/laudo")
public class LaudoController {
    @Autowired
    private LaudoService laudoService;

    @PostMapping("/criar")
    public ResponseEntity<LaudoDTO> createLaudo(@Valid @RequestBody LaudoDTO laudoCreateDTO) {
        LaudoDTO novoLaudo = laudoService.createLaudo(laudoCreateDTO);
        return ResponseEntity.ok(novoLaudo);
    }

    @GetMapping
    public ResponseEntity<List<LaudoDTO>> getAllLaudos() {
        List<LaudoDTO> laudos = laudoService.getAllLaudos();
        return ResponseEntity.ok(laudos);
    }

    @GetMapping("/{laudoId}")
    public ResponseEntity<LaudoDTO> getLaudoById(@PathVariable Long laudoId) {
        LaudoDTO laudo = laudoService.getLaudoById(laudoId);
        return ResponseEntity.ok(laudo);
    }

    @PutMapping("/{laudoId}")
    public ResponseEntity<LaudoDTO> updateLaudo(@PathVariable Long laudoId, @Valid @RequestBody LaudoDTO laudoCreateDTO) {
        LaudoDTO updatedLaudo = laudoService.updateLaudo(laudoId, laudoCreateDTO);
        return ResponseEntity.ok(updatedLaudo);
    }

    @DeleteMapping("/{laudoId}")
    public ResponseEntity<?> deleteLaudo(@PathVariable Long laudoId) {
        laudoService.deleteLaudo(laudoId);
        return ResponseEntity.ok("Laudo removido com sucesso");
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<LaudoDTO>> getLaudosByPaciente(@PathVariable Long pacienteId) {
        List<LaudoDTO> laudos = laudoService.getLaudosByPaciente(pacienteId);
        return ResponseEntity.ok(laudos);
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<List<LaudoDTO>> getLaudosByConsulta(@PathVariable Long consultaId) {
        List<LaudoDTO> laudos = laudoService.getLaudosByConsulta(consultaId);
        return ResponseEntity.ok(laudos);
    }
}
