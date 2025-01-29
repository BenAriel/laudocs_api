package br.api.laudocs.laudocs_api.service;


import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void enviarAtualizacaoConsulta(Consulta consulta) {
        messagingTemplate.convertAndSend("/topic/consultas", consulta);
    }
}
