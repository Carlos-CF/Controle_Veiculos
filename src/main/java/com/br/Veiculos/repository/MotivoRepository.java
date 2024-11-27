package com.br.Veiculos.repository;

import com.br.Veiculos.model.Motivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoRepository extends JpaRepository<Motivo, Long> {

    public boolean existsByNome(String nome);
}
