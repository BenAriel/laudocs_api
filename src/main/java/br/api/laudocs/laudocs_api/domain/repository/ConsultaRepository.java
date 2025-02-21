package br.api.laudocs.laudocs_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;
import br.api.laudocs.laudocs_api.enums.Status;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    boolean existsByPacienteIdAndStatus(Long pacienteId, Status status);
}
