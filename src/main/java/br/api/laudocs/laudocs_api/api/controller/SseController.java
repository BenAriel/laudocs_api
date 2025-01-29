package br.api.laudocs.laudocs_api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import br.api.laudocs.laudocs_api.service.SseService;

@RestController
@RequestMapping("/sse")
public class SseController {

    @Autowired
    private SseService sseService;

    // Endpoint para o frontend se inscrever para receber eventos
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return sseService.subscribe();
    }
}