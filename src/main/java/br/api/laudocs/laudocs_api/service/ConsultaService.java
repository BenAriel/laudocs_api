package br.api.laudocs.laudocs_api.service;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOrequest;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOresponse;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOupdate;
import br.api.laudocs.laudocs_api.domain.repository.ConsultaRepository;
import br.api.laudocs.laudocs_api.domain.repository.PacienteRepository;
import br.api.laudocs.laudocs_api.exception.ValidationException;
import br.api.laudocs.laudocs_api.exception.ValidationUtils;
import br.api.laudocs.laudocs_api.domain.entities.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    ConsultaRepository repo;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    private SseService sseService;

    public ConsultaDTOresponse createConsulta(ConsultaDTOrequest consultaDTO) {
        ValidationUtils.checkVazio(consultaDTO.getDataConsulta(), "Data da consulta não pode ser vazia.");
        ValidationUtils.checkVazio(consultaDTO.getMedicoSolicitante(), "Médico solicitante não pode ser vazio.");
        
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + consultaDTO.getPacienteId()));

        Consulta consulta = new Consulta(consultaDTO, paciente);
        consulta = repo.save(consulta);

        sseService.sendEvent("consulta-adicionada", consulta);
        
        return new ConsultaDTOresponse(consulta);
    }

    public ConsultaDTOresponse getConsulta(Long id) {
        Consulta consulta = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));
    
        return new ConsultaDTOresponse(consulta);
    }

    public List<ConsultaDTOresponse> getAllConsultas() {
        List<Consulta> consultas = repo.findAll();
    
        return consultas.stream()
            .map(consulta -> new ConsultaDTOresponse(consulta))
            .collect(Collectors.toList());
    }
    
    public ConsultaDTOresponse updateConsulta(ConsultaDTOupdate consultaDTO) {
        Consulta consulta = repo.findById(consultaDTO.getId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));
    
        ValidationUtils.checkVazio(consultaDTO.getMedicoSolicitante(), "Médico solicitante não pode ser vazio.");
        ValidationUtils.checkVazio(consultaDTO.getPacienteId(), "Paciente não informado.");
    
        consulta.setMedicoSolicitante(consultaDTO.getMedicoSolicitante());
    
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
            .orElseThrow(() -> new ValidationException("Paciente não encontrado."));

        ValidationUtils.checkVazio(consultaDTO.getNomePaciente(), "Paciente não pode ter nome vazio.");
        ValidationUtils.checkVazio(consultaDTO.getDataNascPaciente(), "Paciente não pode ter data de nascimento vazio.");

        int idade = calcularIdade(consultaDTO.getDataNascPaciente());
        paciente.setDataNasc(consultaDTO.getDataNascPaciente());
        paciente.setIdade(idade);
        paciente.setNome(consultaDTO.getNomePaciente());

        consulta.setPaciente(paciente);
    
        pacienteRepository.save(paciente);
        Consulta consultaAtualizada = repo.save(consulta);

        sseService.sendEvent("consulta-atualizada", consultaAtualizada);
        
        return new ConsultaDTOresponse(consultaAtualizada);
    }

    private int calcularIdade(LocalDate dataNasc) {
        LocalDate hoje = LocalDate.now();
        return hoje.getYear() - dataNasc.getYear() -
                (hoje.getDayOfYear() < dataNasc.getDayOfYear() ? 1 : 0);
    }

    public void deleteConsulta(Long id) {
        Consulta consulta = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));

        repo.deleteById(id);

        sseService.sendEvent("consulta-removida", consulta);
       
    }
}
