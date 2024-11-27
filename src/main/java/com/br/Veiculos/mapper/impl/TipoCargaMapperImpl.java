package com.br.Veiculos.mapper.impl;

import com.br.Veiculos.mapper.CustomObjectMapper;
import com.br.Veiculos.model.TipoCarga;
import com.br.Veiculos.model.dto.TipoCargaDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TipoCargaMapperImpl implements CustomObjectMapper<TipoCarga, TipoCargaDTO> {

    @Override
    public TipoCargaDTO converterParaDto(TipoCarga entity) {

        TipoCargaDTO dto = new TipoCargaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setStatus(entity.isStatus());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setUltimaAtualizacao(entity.getUltimaAtualizacao());

        return dto;
    }

    @Override
    public TipoCarga converterParaEntidade(TipoCargaDTO dto) {

        TipoCarga tipoCarga = new TipoCarga();
        tipoCarga.setId(dto.getId());
        tipoCarga.setNome(dto.getNome());
        tipoCarga.setStatus(dto.isStatus());
        tipoCarga.setDataCriacao(dto.getDataCriacao());
        tipoCarga.setUltimaAtualizacao(dto.getUltimaAtualizacao());

        return  tipoCarga;
    }

    @Override
    public List<TipoCargaDTO> converterParaListaDeDtos(List<TipoCarga> entityList) {

        List<TipoCargaDTO> lista = new ArrayList<>();
        for (TipoCarga entity : entityList){
            lista.add(converterParaDto(entity));
        }
        return lista;
    }
}
