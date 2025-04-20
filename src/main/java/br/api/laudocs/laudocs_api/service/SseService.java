package br.api.laudocs.laudocs_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SseService {

    private static final Logger logger = LoggerFactory.getLogger(SseService.class);
    private final List<SseEmitter> emitters = new ArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        logger.info("[SSE] Novo inscrito no SSE. Total de emitters agora: {}", emitters.size());

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            logger.info("Emitter removido após completar. Total de emitters agora: {}", emitters.size());
        });
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            logger.warn("Emitter removido após timeout. Total de emitters agora: {}", emitters.size());
        });
        emitter.onError((e) -> {
            emitters.remove(emitter);
            logger.error("Emitter removido após erro: {}. Total de emitters agora: {}", e.getMessage(), emitters.size());
        });

        return emitter;
    }

    public void sendEvent(String eventName, Object data) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        logger.info("Enviando evento '{}' para {} emitters.", eventName, emitters.size());

        for (SseEmitter emitter : new ArrayList<>(emitters)) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (IOException | IllegalStateException e) {
                deadEmitters.add(emitter); // Adiciona emitters que falharam para remoção
                logger.warn("Emitter removido durante envio. Motivo: {}. Total antes da remoção: {}", e.getMessage(), emitters.size());
            }
        }

        emitters.removeAll(deadEmitters);
        if (!deadEmitters.isEmpty()) {
            logger.info("{} emitters removidos após falha de envio. Total de emitters agora: {}", deadEmitters.size(), emitters.size());
        }
    }
}