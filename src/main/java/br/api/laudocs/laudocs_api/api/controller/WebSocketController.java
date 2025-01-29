package br.api.laudocs.laudocs_api.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/consultas")
    @SendTo("/topic/consultas")
    public String handleConsulta(String message) {
        return message;
    }
}
