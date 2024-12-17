package br.api.laudocs.laudocs_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.api.laudocs.laudocs_api.domain.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    
}
