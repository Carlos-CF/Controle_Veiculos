package com.br.Veiculos.mapper.impl;

import com.br.Veiculos.mapper.CustomObjectMapper;
import com.br.Veiculos.model.Departamento;
import com.br.Veiculos.model.dto.DepartamentoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartamentoMapperImpl implements CustomObjectMapper<Departamento, DepartamentoDTO> {

    @Override
    public DepartamentoDTO converterParaDto(Departamento entity) {

        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setStatus(entity.isStatus());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setUltimaAtualizacao(entity.getUltimaAtualizacao());

        return dto;
    }

    @Override
    public Departamento converterParaEntidade(DepartamentoDTO dto) {

        Departamento departamento = new Departamento();
        departamento.setId(dto.getId());
        departamento.setNome(dto.getNome());
        departamento.setStatus(dto.isStatus());
        departamento.setDataCriacao(dto.getDataCriacao());
        departamento.setUltimaAtualizacao(dto.getUltimaAtualizacao());

        return departamento;
    }

    @Override
    public List<DepartamentoDTO> converterParaListaDeDtos(List<Departamento> entityList) {

        List<DepartamentoDTO> lista = new ArrayList<>();
        for (Departamento entity : entityList){
            lista.add(converterParaDto(entity));
        }
        return lista;
    }
}
