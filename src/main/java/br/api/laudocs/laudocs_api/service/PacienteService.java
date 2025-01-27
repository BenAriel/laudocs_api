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

        return new PacienteDTO(op.get());
    }

    public PacienteDTO getPacienteByCpf(String cpf) {
        var op = repo.findByCpf(cpf);

        if (!op.isPresent())
            throw new ValidationException("Paciente não encontrado.");

        return new PacienteDTO(op.get());
    }

    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        ValidationUtils.checkVazio(pacienteDTO.getNome(), "Nome não pode ser vazio.");
        ValidationUtils.checkVazio(pacienteDTO.getCpf(), "CPF não pode ser vazio.");

        if (getCpfExistId(pacienteDTO.getCpf()) != null)
            throw new ValidationException("CPF já cadastrado no sistema.");

        if (pacienteDTO.getIdade() == 0 && pacienteDTO.getDataNasc() == null)
            throw new ValidationException("Informe idade ou data de nascimento.");

        
        Paciente paciente = repo.save(new Paciente(pacienteDTO));
        return new PacienteDTO(paciente);
    }

    public PacienteDTO updatePaciente(PacienteDTO pacienteDTO) {
        var op = repo.findById(pacienteDTO.getId());

        if (!op.isPresent())
            throw new ValidationException("Paciente não existe.");

        ValidationUtils.checkVazio(pacienteDTO.getNome(), "Nome não pode ser vazio.");
        ValidationUtils.checkVazio(pacienteDTO.getCpf(), "CPF não pode ser vazio.");

        Long idCpf = getCpfExistId(pacienteDTO.getCpf());
        if (idCpf != null && idCpf != pacienteDTO.getId())
            throw new ValidationException("CPF já cadastrado no sistema.");

        if (pacienteDTO.getIdade() == 0 && pacienteDTO.getDataNasc() == null)
            throw new ValidationException("Informe idade ou data de nascimento.");

        Paciente paciente = op.get();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setDataNasc(pacienteDTO.getDataNasc());
        paciente.setIdade(pacienteDTO.getIdade());

        return new PacienteDTO(repo.save(paciente));
        
    }

    public void removePaciente(Long pacienteId) {
        var op = repo.findById(pacienteId);

        if (!op.isPresent())
            throw new ValidationException("Paciente não existe.");

        repo.deleteById(pacienteId);
    }

    public Long getCpfExistId(String cpf) {
        var op = repo.findByCpf(cpf);
        if (op.isPresent())
            return op.get().getId();

        return null;
    }
}
