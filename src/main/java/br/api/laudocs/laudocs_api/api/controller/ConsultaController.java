package br.api.laudocs.laudocs_api.api.controller;


import br.api.laudocs.laudocs_api.api.dto.ConsultaDTO;
import br.api.laudocs.laudocs_api.service.ConsultaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultas")
public class ConsultaController {
    @Autowired
    ConsultaService service;

    @PostMapping
    public ResponseEntity<ConsultaDTO> criarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        ResponseEntity<ConsultaDTO> response = new ResponseEntity<ConsultaDTO>(
                service.createConsulta(consultaDTO), HttpStatus.OK);
        return response;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas() {
        ResponseEntity<List<ConsultaDTO>> response =
                new ResponseEntity<List<ConsultaDTO>>(service.getAllConsultas(),
                        HttpStatus.OK);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarConsultaPorId(@PathVariable Long id) {
        ResponseEntity<ConsultaDTO> response =
                new ResponseEntity<ConsultaDTO>(service.getConsulta(id), HttpStatus.OK);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizarConsulta( @RequestBody ConsultaDTO consultaDTO) {
        
        ResponseEntity<ConsultaDTO> response = new ResponseEntity<ConsultaDTO>(
                service.updateConsulta(consultaDTO), HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConsulta(@PathVariable Long id) {
        service.deleteConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
