package com.br.Veiculos.repository;

import com.br.Veiculos.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {

    public boolean existsByNome(String nome);

    public boolean existsByCnpj(String cnpj);
    public boolean existsByNomeAndId(String nome, Long id);

    public boolean existsByCnpjAndId(String cnpj, Long id);

    public Optional<Transportadora> findByNome(String nome);
}
