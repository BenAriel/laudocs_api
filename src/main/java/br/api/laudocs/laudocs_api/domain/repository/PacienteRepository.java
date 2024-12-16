package br.api.laudocs.laudocs_api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.api.laudocs.laudocs_api.domain.entities.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);   
}
