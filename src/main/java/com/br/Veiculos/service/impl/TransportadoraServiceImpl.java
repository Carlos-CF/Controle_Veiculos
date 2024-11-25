package com.br.Veiculos.service.impl;

import com.br.Veiculos.mapper.CustomObjectMapper;
import com.br.Veiculos.model.Transportadora;
import com.br.Veiculos.model.dto.TransportadoraDTO;
import com.br.Veiculos.repository.TransportadoraRepository;
import com.br.Veiculos.service.TransportadoraService;
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
public class TransportadoraServiceImpl implements TransportadoraService {

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Autowired
    private CustomObjectMapper<Transportadora, TransportadoraDTO> transportadoraMapper;

    @Override
    public ResponseEntity<Object> mudarStatus(Long idObjeto) throws Exception {

        Transportadora objeto = transportadoraRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("A Transportadora com ID " + idObjeto + " não foi encontrado!"));

        objeto.setStatus(!objeto.isStatus());
        objeto.setUltimaAtualizacao(LocalDateTime.now());
        Transportadora objetoAtualizado = transportadoraRepository.saveAndFlush(objeto);

        TransportadoraDTO objetoDTO = transportadoraMapper.converterParaDto(objetoAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(objetoDTO));
    }

    @Override
    public ResponseEntity<Object> cadastrar(TransportadoraDTO objeto) throws Exception {


        if (transportadoraRepository.existsByNome(objeto.getNome())){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar a Transportadora. Já existe outra Transportadora com o mesmo nome."));
        }

        if (transportadoraRepository.existsByCnpj(objeto.getCnpj())){
            return  ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar a Transportadora. Já existe outra Transportadora com o mesmo CNPJ."));
        }


        Transportadora transportadora = new Transportadora();
        transportadora.setNome(objeto.getNome());
        transportadora.setStatus(objeto.isStatus());
        transportadora.setCnpj(objeto.getCnpj());

        Transportadora objetoCriado = transportadoraRepository.saveAndFlush(transportadora);
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

        List<TransportadoraDTO> transportadoraDTOs = transportadoraMapper.converterParaListaDeDtos(transportadoraRepository.findAll());
        if (transportadoraDTOs.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Não existe Transportadora cadastrada no Sistema"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(transportadoraDTOs));
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, TransportadoraDTO objeto) throws Exception {

        Transportadora dadosDto = transportadoraMapper.converterParaEntidade(objeto);
        Transportadora paraEditar = transportadoraRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("A Transportadora com ID " + idObjeto + " não foi encontrada!"));

        if (transportadoraRepository.existsByNome(objeto.getNome())){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar a Transportadora. Já existe outra Transportadora com o mesmo nome."));
        }

        if (transportadoraRepository.existsByCnpjAndIdNot(objeto.getCnpj(), objeto.getId())){
            return  ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar a Transportadora. Já existe outra Transportadora com o mesmo CNPJ."));
        }


        LocalDateTime dataHora = paraEditar.getDataCriacao();

        dadosDto.setDataCriacao(dataHora);
        dadosDto.setUltimaAtualizacao(LocalDateTime.now());
        dadosDto.setId(idObjeto);
        dadosDto.setStatus(paraEditar.isStatus());
        BeanUtils.copyProperties(dadosDto, paraEditar, "id");
        Transportadora objetoAtualizado = transportadoraRepository.saveAndFlush(dadosDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(transportadoraMapper.converterParaDto(objetoAtualizado)));

    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {

        Transportadora transportadora = transportadoraRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("A Transportadora com ID " + idObjeto + " não foi encontrada!"));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(transportadoraMapper.converterParaDto(transportadora)));
    }

    @Override
    public ResponseEntity<Object> excluir(Long idObjeto) throws Exception {

        transportadoraRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("A Transportadora com ID " + idObjeto + " não foi encontrada!"));

        transportadoraRepository.deleteById(idObjeto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("A Transportadora foi excluído com sucesso."));
    }
}
