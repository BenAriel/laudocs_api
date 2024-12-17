package br.api.laudocs.laudocs_api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.api.laudocs.laudocs_api.domain.entities.Laudo;

public interface LaudoRepository  extends JpaRepository<Laudo, Long> {
    List<Laudo> findByPacienteId(Long pacienteId);
    List<Laudo> findByConsultaId(Long consultaId);
}
