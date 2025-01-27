package br.api.laudocs.laudocs_api.service;


import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOrequest;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTOresponse;
import br.api.laudocs.laudocs_api.domain.repository.ConsultaRepository;
import br.api.laudocs.laudocs_api.domain.repository.PacienteRepository;
import br.api.laudocs.laudocs_api.exception.ValidationException;
import br.api.laudocs.laudocs_api.exception.ValidationUtils;
import br.api.laudocs.laudocs_api.domain.entities.Paciente;
import br.api.laudocs.laudocs_api.domain.entities.Laudo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    ConsultaRepository repo;

    @Autowired
    PacienteRepository pacienteRepository;

    public ConsultaDTOresponse createConsulta(ConsultaDTOrequest consultaDTO) {
        ValidationUtils.checkVazio(consultaDTO.getDataConsulta(), "Data da consulta não pode ser vazia.");
        ValidationUtils.checkVazio(consultaDTO.getMedicoSolicitante(), "Médico solicitante não pode ser vazio.");
        
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + consultaDTO.getPacienteId()));

        Consulta consulta = new Consulta(consultaDTO, paciente);
        consulta = repo.save(consulta);
        
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
    
    

    public ConsultaDTOresponse updateConsulta(ConsultaDTOrequest consultaDTO) {
        Consulta consulta = repo.findById(consultaDTO.getId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));
    
        ValidationUtils.checkVazio(consultaDTO.getDataConsulta(), "Data da consulta não pode ser vazia.");
        ValidationUtils.checkVazio(consultaDTO.getMedicoSolicitante(), "Médico solicitante não pode ser vazio.");
        ValidationUtils.checkVazio(consultaDTO.getPacienteId(), "Paciente não informado.");
    
        consulta.setDataConsulta(consultaDTO.getDataConsulta());
        consulta.setMedicoSolicitante(consultaDTO.getMedicoSolicitante());
    
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
            .orElseThrow(() -> new ValidationException("Paciente não encontrado."));
        consulta.setPaciente(paciente);
    
        if (consultaDTO.getLaudoId() != null) {
            Laudo laudo = new Laudo();
            laudo.setId(consultaDTO.getLaudoId());
            consulta.setLaudo(laudo);
        }
    
        Consulta consultaAtualizada = repo.save(consulta);
        return new ConsultaDTOresponse(consultaAtualizada);
    }

    public void deleteConsulta(Long id) {
        repo.deleteById(id);
    }
}

