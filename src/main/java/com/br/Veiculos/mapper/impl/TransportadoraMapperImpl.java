package com.br.Veiculos.mapper.impl;

import com.br.Veiculos.mapper.CustomObjectMapper;
import com.br.Veiculos.model.Transportadora;
import com.br.Veiculos.model.dto.TransportadoraDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransportadoraMapperImpl implements CustomObjectMapper<Transportadora, TransportadoraDTO> {

    @Override
    public TransportadoraDTO converterParaDto(Transportadora entity) {

        TransportadoraDTO dto = new TransportadoraDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCnpj(entity.getCnpj());
        dto.setStatus(entity.isStatus());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setUltimaAtualizacao(entity.getUltimaAtualizacao());

        return dto;
    }

    @Override
    public Transportadora converterParaEntidade(TransportadoraDTO dto) {

        Transportadora transportadora = new Transportadora();
        transportadora.setId(dto.getId());
        transportadora.setNome(dto.getNome());
        transportadora.setCnpj(dto.getCnpj());
        transportadora.setStatus(dto.isStatus());
        transportadora.setDataCriacao(dto.getDataCriacao());
        transportadora.setUltimaAtualizacao(dto.getUltimaAtualizacao());

        return transportadora;
    }

    @Override
    public List<TransportadoraDTO> converterParaListaDeDtos(List<Transportadora> entityList) {

        List<TransportadoraDTO> lista = new ArrayList<>();
        for (Transportadora entity : entityList){
            lista.add(converterParaDto(entity));
        }
        return lista;
    }
}
