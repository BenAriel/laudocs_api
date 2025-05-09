package br.api.laudocs.laudocs_api.api.controller;


import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOrequest;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOresponse;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOupdate;
import br.api.laudocs.laudocs_api.exception.ValidationException;
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
    public ResponseEntity<ConsultaDTOresponse> criarConsulta(@RequestBody ConsultaDTOrequest consultaDTO) {
        try {
            ConsultaDTOresponse response = service.createConsulta(consultaDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // existe consulta ativa, retorna 409
        }
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDTOresponse>> listarConsultas() {
        ResponseEntity<List<ConsultaDTOresponse>> response =
                new ResponseEntity<List<ConsultaDTOresponse>>(service.getConsultas(),
                        HttpStatus.OK);
        return response;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConsultaDTOresponse>> allConsultas() {
        ResponseEntity<List<ConsultaDTOresponse>> response =
                new ResponseEntity<List<ConsultaDTOresponse>>(service.getAllConsultas(),
                        HttpStatus.OK);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTOresponse> buscarConsultaPorId(@PathVariable Long id) {
        ResponseEntity<ConsultaDTOresponse> response =
                new ResponseEntity<ConsultaDTOresponse>(service.getConsulta(id), HttpStatus.OK);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTOresponse> atualizarConsulta( @RequestBody ConsultaDTOupdate consultaDTO) {
        
        ResponseEntity<ConsultaDTOresponse> response = new ResponseEntity<ConsultaDTOresponse>(
                service.updateConsulta(consultaDTO), HttpStatus.OK);
        return response;
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ConsultaDTOresponse> atualizarStatusConsulta(@PathVariable Long id) {
        ResponseEntity<ConsultaDTOresponse> response = new ResponseEntity<ConsultaDTOresponse>(
                service.updasteStatus(id), HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConsulta(@PathVariable Long id) {
        service.deleteConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
