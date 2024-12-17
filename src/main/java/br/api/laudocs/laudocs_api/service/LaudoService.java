package br.api.laudocs.laudocs_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.laudocs.laudocs_api.api.dto.LaudoDTO;
import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;
import br.api.laudocs.laudocs_api.domain.entities.Paciente;
import br.api.laudocs.laudocs_api.domain.repository.ConsultaRepository;
import br.api.laudocs.laudocs_api.domain.repository.LaudoRepository;
import br.api.laudocs.laudocs_api.domain.repository.PacienteRepository;

@Service
public class LaudoService {
    @Autowired
    private LaudoRepository laudoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public LaudoDTO createLaudo(LaudoDTO laudoDTO) {

        //Não necessariamente precisa ter um paciente ou consulta
        Paciente paciente = pacienteRepository.findById(laudoDTO.getPacienteId()).orElse(null);
        Consulta consulta = consultaRepository.findById(laudoDTO.getConsultaId()).orElse(null);

        Laudo laudo = new Laudo();
        laudo.setPaciente(paciente);
        laudo.setConsulta(consulta);
        laudo.setType(laudoDTO.getType());
        laudo.setSize(laudoDTO.getSize());
        laudo.setUrl(laudoDTO.getUrl());

        laudo = laudoRepository.save(laudo);
        return new LaudoDTO(laudo);
    }

    public List<LaudoDTO> getAllLaudos() {
        return laudoRepository.findAll()
                .stream()
                .map(LaudoDTO::new)
                .collect(Collectors.toList());
    }

    public LaudoDTO getLaudoById(Long laudoId) {
        Laudo laudo = laudoRepository.findById(laudoId)
                .orElseThrow(() -> new RuntimeException("Laudo não encontrado"));
        return new LaudoDTO(laudo);
    }

    public LaudoDTO updateLaudo(Long laudoId, LaudoDTO laudoDTO) {
        Laudo laudo = laudoRepository.findById(laudoId)
                .orElseThrow(() -> new RuntimeException("Laudo não encontrado"));

        laudo.setType(laudoDTO.getType());
        laudo.setSize(laudoDTO.getSize());
        laudo.setUrl(laudoDTO.getUrl());

        laudo = laudoRepository.save(laudo);
        return new LaudoDTO(laudo);
    }

    public void deleteLaudo(Long laudoId) {
        Laudo laudo = laudoRepository.findById(laudoId)
                .orElseThrow(() -> new RuntimeException("Laudo não encontrado"));
        laudoRepository.delete(laudo);
    }

    public List<LaudoDTO> getLaudosByPaciente(Long pacienteId) {
        List<Laudo> laudos = laudoRepository.findByPacienteId(pacienteId);
        return laudos.stream()
                     .map(LaudoDTO::new)
                     .toList();
    }

    public List<LaudoDTO> getLaudosByConsulta(Long consultaId) {
        List<Laudo> laudos = laudoRepository.findByConsultaId(consultaId);
        return laudos.stream()
                     .map(LaudoDTO::new)
                     .toList();
    }
}
