package com.br.Veiculos.repository;

import com.br.Veiculos.model.TipoCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCargaRepository extends JpaRepository<TipoCarga, Long> {

    public boolean existsByNome(String nome);
}
