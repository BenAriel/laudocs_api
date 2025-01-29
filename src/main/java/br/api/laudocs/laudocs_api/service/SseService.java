package br.api.laudocs.laudocs_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SseService {

    private final List<SseEmitter> emitters = new ArrayList<>();

    // Método para adicionar um novo cliente (frontend) à lista de emissores
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Tempo de vida indefinido
        emitters.add(emitter);

        // Remove o emitter quando o cliente se desconectar
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    // Método para enviar um evento para todos os clientes conectados
    public void sendEvent(String eventName, Object data) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName) // Nome do evento
                        .data(data)); // Dados do evento
            } catch (IOException e) {
                deadEmitters.add(emitter); // Adiciona emitters que falharam para remoção
            }
        }

        // Remove emitters que falharam
        emitters.removeAll(deadEmitters);
    }
}