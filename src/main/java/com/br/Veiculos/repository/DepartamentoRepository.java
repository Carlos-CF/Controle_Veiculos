package com.br.Veiculos.repository;

import com.br.Veiculos.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    public boolean existsByNome(String nome);
}
