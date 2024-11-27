package com.br.Veiculos.service.impl;

import com.br.Veiculos.mapper.CustomObjectMapper;
import com.br.Veiculos.model.TipoCarga;
import com.br.Veiculos.model.dto.TipoCargaDTO;
import com.br.Veiculos.repository.TipoCargaRepository;
import com.br.Veiculos.service.TipoCargaService;
import com.br.Veiculos.service.util.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TipoCargaServiceImpl implements TipoCargaService {

    @Autowired
    private TipoCargaRepository tipoCargaRepository;

    @Autowired
    private CustomObjectMapper<TipoCarga, TipoCargaDTO> tipoCargaMapper;

    @Override
    public ResponseEntity<Object> mudarStatus(Long idObjeto) throws Exception {

        TipoCarga objeto = tipoCargaRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O TipoCarga com ID " + idObjeto + " não foi encontrado!"));

        objeto.setStatus(!objeto.isStatus());
        objeto.setUltimaAtualizacao(LocalDateTime.now());
        TipoCarga objetoAtualizado = tipoCargaRepository.saveAndFlush(objeto);

        TipoCargaDTO objetoDTO = tipoCargaMapper.converterParaDto(objetoAtualizado);

        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(objetoDTO));
    }

    @Override
    public ResponseEntity<Object> cadastrar(TipoCargaDTO objeto) throws Exception {

        if (tipoCargaRepository.existsByNome(objeto.getNome())){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar o TipoCarga. Já existe outro TipoCarga com o mesmo nome."));
        }

        TipoCarga tipoCarga = new TipoCarga();
        tipoCarga.setNome(objeto.getNome());

        TipoCarga objetoCriado = tipoCargaRepository.saveAndFlush(tipoCarga);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/{id}")
                                .buildAndExpand(objetoCriado.getId())
                                .toUri())
                .build();
    }

    @Override
    public ResponseEntity<Object> listar() throws Exception {

        List<TipoCargaDTO> tipoCargaDTOs = tipoCargaMapper.converterParaListaDeDtos(tipoCargaRepository.findAll());
        if (tipoCargaDTOs.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Não existe TipoCarga cadastrados no sistemas."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(tipoCargaDTOs));
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, TipoCargaDTO objeto) throws Exception {

        TipoCarga dadosDto = tipoCargaMapper.converterParaEntidade(objeto);
        TipoCarga paraEditar = tipoCargaRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O TipoCarga com ID " + idObjeto + " não foi encontrado!"));

        if (tipoCargaRepository.existsByNome(objeto.getNome())){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar o TipoCarga. Já existe outro TipoCarga com o mesmo nome."));
        }
         LocalDateTime dataHora = paraEditar.getDataCriacao();

        dadosDto.setDataCriacao(dataHora);
        dadosDto.setUltimaAtualizacao(LocalDateTime.now());
        dadosDto.setId(idObjeto);
        dadosDto.setStatus(dadosDto.isStatus());
        BeanUtils.copyProperties(dadosDto, paraEditar, "id");
        TipoCarga objetoAtualizado = tipoCargaRepository.saveAndFlush(dadosDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(tipoCargaMapper.converterParaDto(objetoAtualizado)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {

        TipoCarga tipoCarga = tipoCargaRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O TipoCarga com ID " + idObjeto + " não foi encontrado!"));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(tipoCargaMapper.converterParaDto(tipoCarga)));
    }

    @Override
    public ResponseEntity<Object> excluir(Long idObjeto) throws Exception {

        tipoCargaRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O TipoCarga com ID " + idObjeto + " não foi encontrado!"));

        tipoCargaRepository.deleteById(idObjeto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("O TipoCarga foi excluído com sucesso."));
    }
}
