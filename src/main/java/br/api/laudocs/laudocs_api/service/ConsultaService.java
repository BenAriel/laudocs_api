package br.api.laudocs.laudocs_api.service;


import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.api.dto.ConsultaDTO;
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

    public ConsultaDTO createConsulta(ConsultaDTO consultaDTO) {
        ValidationUtils.checkVazio(consultaDTO.getDataConsulta(), "Data da consulta não pode ser vazia.");
        ValidationUtils.checkVazio(consultaDTO.getMedicoSolicitante(), "Médico solicitante não pode ser vazio.");
        
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + consultaDTO.getPacienteId()));

        Consulta consulta = new Consulta(consultaDTO, paciente);
        consulta = repo.save(consulta);
        
        return new ConsultaDTO(consulta);
        
    }

    public List<ConsultaDTO> getAllConsultas() {
        List<Consulta> consultas = repo.findAll();

         return consultas.stream().map(ConsultaDTO::toDTO).collect(Collectors.toList());
    }

    public ConsultaDTO getConsulta(Long idConsulta) {
        Consulta consulta = repo.findById(idConsulta)
                                .orElseThrow(() -> new ValidationException("Consulta não encontrada."));
        return ConsultaDTO.toDTO(consulta);
    }
    

    public ConsultaDTO updateConsulta(ConsultaDTO consultaDTO) {
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
        return ConsultaDTO.toDTO(consultaAtualizada);
    }

    public void deleteConsulta(Long id) {
        repo.deleteById(id);
    }
}

