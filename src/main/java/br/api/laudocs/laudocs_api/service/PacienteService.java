package br.api.laudocs.laudocs_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.laudocs.laudocs_api.api.dto.PacienteDTO;
import br.api.laudocs.laudocs_api.domain.entities.Paciente;
import br.api.laudocs.laudocs_api.domain.repository.PacienteRepository;
import br.api.laudocs.laudocs_api.exception.ValidationUtils;
import br.api.laudocs.laudocs_api.exception.ValidationException;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository repo;

    public List<PacienteDTO> getAllPacientes() {
        List<Paciente> pacientes = repo.findAll();

        return pacientes.stream().map(PacienteDTO::toDTO).collect(Collectors.toList());
    }

    public PacienteDTO getPaciente(Long idPaciente) {
        var op = repo.findById(idPaciente);

        if (!op.isPresent())
            throw new ValidationException("Paciente não encontrado.");

        return PacienteDTO.toDTO(op.get());
    }

    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        ValidationUtils.checkVazio(pacienteDTO.getNome(), "Nome não pode ser vazio.");
        ValidationUtils.checkVazio(pacienteDTO.getCpf(), "CPF não pode ser vazio.");

        if (getCpfExistId(pacienteDTO.getCpf()) != null)
            throw new ValidationException("CPF já cadastrado no sistema.");

        if (pacienteDTO.getIdade() == 0 && pacienteDTO.getDataNasc() == null)
            throw new ValidationException("Informe idade ou data de nascimento.");

        Paciente paciente = repo.save(new Paciente(pacienteDTO));
        return PacienteDTO.toDTO(paciente);
    }

    public PacienteDTO updatePaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = repo.findById(pacienteDTO.getId());
    
        if (paciente == null) {
            throw new ValidationException("Paciente não existe.");
        }

        ValidationUtils.checkVazio(pacienteDTO.getNome(), "Nome não pode ser vazio.");
        ValidationUtils.checkVazio(pacienteDTO.getCpf(), "CPF não pode ser vazio.");

        Long idCpf = getCpfExistId(pacienteDTO.getCpf());
        if (idCpf != null && idCpf != pacienteDTO.getId())
            throw new ValidationException("CPF já cadastrado no sistema.");

        if (pacienteDTO.getIdade() == 0 && pacienteDTO.getDataNasc() == null)
            throw new ValidationException("Informe idade ou data de nascimento.");

        paciente.setNome(pacienteDTO.getNome());
        paciente.setCPF(pacienteDTO.getCpf());
        paciente.setDataNasc(pacienteDTO.getDataNasc());
        paciente.setIdade(pacienteDTO.getIdade());

        Paciente pacienteAtualizado = repo.save(paciente);

        return PacienteDTO.toDTO(pacienteAtualizado);

    }

    public void removePaciente(Long pacienteId) {
        var op = repo.findById(pacienteId);

        if (!op.isPresent())
            throw new ValidationException("Paciente não existe.");

        repo.deleteById(pacienteId);
    }

    public Long getCpfExistId(String cpf) {
        Paciente paciente = repo.findByCPF(cpf);
        if (paciente != null) {
            return paciente.getId();
        }
        return null;
    }
}
