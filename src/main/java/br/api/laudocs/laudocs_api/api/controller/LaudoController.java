package br.api.laudocs.laudocs_api.api.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.api.laudocs.laudocs_api.api.dto.LaudoDTOresponse;
import br.api.laudocs.laudocs_api.api.dto.LaudoDTOrequest;
import br.api.laudocs.laudocs_api.api.dto.LaudoDTOresend;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;
import br.api.laudocs.laudocs_api.service.LaudoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/laudo")
public class LaudoController {
    @Autowired
    private LaudoService laudoService;

    @PostMapping("/criar")
    public ResponseEntity<LaudoDTOresponse> createLaudo(@Valid @ModelAttribute LaudoDTOrequest laudoCreateDTO) throws IOException {
        LaudoDTOresponse novoLaudo = laudoService.save(laudoCreateDTO);
        return ResponseEntity.ok(novoLaudo);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<byte[]> get(@PathVariable Long documentId) {
        Laudo response = laudoService.find(documentId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + response.getUrl() + "\"");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("alterar/{documentId}")
    public ResponseEntity<LaudoDTOresponse> resendDocument(@PathVariable Long documentId, @ModelAttribute @Valid LaudoDTOresend request) throws IOException {
            LaudoDTOresponse response = laudoService.update(request,documentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
     @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> delete(@PathVariable Long documentId) {
        laudoService.delete(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
