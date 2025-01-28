package br.api.laudocs.laudocs_api.service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.api.laudocs.laudocs_api.api.dto.PacienteDTO;
import br.api.laudocs.laudocs_api.api.dto.PacienteDTOupdate;
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

   public PacienteDTO updatePaciente(PacienteDTOupdate pacienteDTO) {
    var op = repo.findById(pacienteDTO.getId());

    if (!op.isPresent())
        throw new ValidationException("Paciente não existe.");

    ValidationUtils.checkVazio(pacienteDTO.getNome(), "Nome não pode ser vazio.");

    if (pacienteDTO.getDataNasc() == null)
        throw new ValidationException("Data de nascimento não pode ser nula.");

    int idade = calcularIdade(pacienteDTO.getDataNasc());

    Paciente paciente = op.get();
    paciente.setNome(pacienteDTO.getNome());
    paciente.setDataNasc(pacienteDTO.getDataNasc());
    paciente.setIdade(idade);

    return new PacienteDTO(repo.save(paciente));
}


// Método auxiliar para calcular a idade
private int calcularIdade(LocalDate dataNasc) {
    LocalDate hoje = LocalDate.now();
    return hoje.getYear() - dataNasc.getYear() -
            (hoje.getDayOfYear() < dataNasc.getDayOfYear() ? 1 : 0);
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
