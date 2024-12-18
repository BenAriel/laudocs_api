package br.api.laudocs.laudocs_api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import br.api.laudocs.laudocs_api.api.dto.LaudoDTOresponse;
import br.api.laudocs.laudocs_api.api.dto.LaudoDTOrequest;
import br.api.laudocs.laudocs_api.api.dto.LaudoDTOresend;
import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;
import br.api.laudocs.laudocs_api.domain.entities.Paciente;
import br.api.laudocs.laudocs_api.domain.repository.ConsultaRepository;
import br.api.laudocs.laudocs_api.domain.repository.LaudoRepository;
import br.api.laudocs.laudocs_api.domain.repository.PacienteRepository;
import br.api.laudocs.laudocs_api.exception.ValidationException;
import jakarta.transaction.Transactional;

@Service
public class LaudoService {
    @Autowired
    private LaudoRepository laudoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    public List<LaudoDTOresponse> list(long patientId) 
    {
        pacienteRepository.findById(patientId).orElseThrow(() -> 
            new ValidationException("Paciente não encontrado")
        );

        Optional<Laudo> documents = laudoRepository.findById(patientId);

        return documents.stream().map(LaudoDTOresponse::new).collect(Collectors.toList());
    }

    @Transactional(rollbackOn = Exception.class)
    public LaudoDTOresponse save(LaudoDTOrequest request) throws IOException {
        Consulta consulta = consultaRepository.findById(request.getConsultaId()).orElseThrow(() -> 
            new ValidationException("Consulta não encontrada.")
        );
        if(consulta.getLaudo() != null) {
            throw new ValidationException("Laudo já cadastrado para essa consulta.");
        }

        Paciente paciente = request.getPacienteId() > 0 ? pacienteRepository.findById(request.getPacienteId()).orElseThrow(() -> 
            new ValidationException("Paciente não encontrado.")
        ) : null;
        MultipartFile file = request.getFile();

        String fileName = Optional.ofNullable(file.getOriginalFilename())
                                .map(StringUtils::cleanPath)
                                .orElse("");

        Laudo document= Laudo.builder()
                                .url(fileName)
                                .paciente(paciente)
                                .contentType(file.getContentType())
                                .size(file.getSize())
                                .content(file.getBytes())
                                .type(request.getType())
                                .consulta(consulta)
                                .build();

        laudoRepository.save(document);

        return new LaudoDTOresponse(document);
    }

    @Transactional(rollbackOn= Exception.class)
    public void delete( Long documentId)  {
        laudoRepository.findById(documentId).orElseThrow(() -> 
            new ValidationException("Paciente não encontrado.")
        );

        laudoRepository.deleteById(documentId);
    }
    @Transactional(rollbackOn= Exception.class)
    public Laudo find( Long documentId) {

        Laudo document = laudoRepository.findById(documentId).orElseThrow(
            () -> new ValidationException("Documento não encontrado.")
        );

        return document;
    }

    @Transactional(rollbackOn = Exception.class)
    public LaudoDTOresponse update(LaudoDTOresend request,Long usuarioId) throws IOException {
        Laudo laudo = laudoRepository.findById(usuarioId).orElseThrow(() -> 
            new ValidationException("Consulta não encontrada.")
        );
        MultipartFile file = request.getFile();

        String fileName = Optional.ofNullable(file.getOriginalFilename())
        .map(StringUtils::cleanPath)
        .orElse("");

    laudo.setUrl(fileName);
laudo.setContentType(file.getContentType());
laudo.setContent(file.getBytes());
laudo.setSize(file.getSize());
laudo.setType(request.getType());

        laudoRepository.save(laudo);

        return new LaudoDTOresponse(laudo);
    }
        
}