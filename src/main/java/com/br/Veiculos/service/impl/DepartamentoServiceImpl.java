package com.br.Veiculos.service.impl;

import com.br.Veiculos.mapper.CustomObjectMapper;
import com.br.Veiculos.model.Departamento;
import com.br.Veiculos.model.dto.DepartamentoDTO;
import com.br.Veiculos.repository.DepartamentoRepository;
import com.br.Veiculos.service.DepartamentoService;
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
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CustomObjectMapper<Departamento, DepartamentoDTO> departamentoMapper;

    @Override
    public ResponseEntity<Object> mudarStatus(Long idObjeto) throws Exception {

        Departamento objeto = departamentoRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O Departamento com ID " + idObjeto + " não foi encontrado!"));

        objeto.setStatus(!objeto.isStatus());
        objeto.setUltimaAtualizacao(LocalDateTime.now());
        Departamento objetoAtualizado = departamentoRepository.saveAndFlush(objeto);

        DepartamentoDTO objetoDTO = departamentoMapper.converterParaDto(objetoAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(objetoDTO));
    }

    @Override
    public ResponseEntity<Object> cadastrar(DepartamentoDTO objeto) throws Exception {

        if (departamentoRepository.existsByNome(objeto.getNome())){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar o Departamento. Já existe outro Departamento com o mesmo nome."));
        }

        Departamento departamento = new Departamento();
        departamento.setNome(objeto.getNome());

        Departamento objetoCriado = departamentoRepository.saveAndFlush(departamento);
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

        List<DepartamentoDTO> departamentoDTOs = departamentoMapper.converterParaListaDeDtos(departamentoRepository.findAll());
        if(departamentoDTOs.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Não existe Departamentos cadastrados no Sistemas"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(departamentoDTOs));
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, DepartamentoDTO objeto) throws Exception {

        Departamento dadosDto = departamentoMapper.converterParaEntidade(objeto);
        Departamento paraEditar = departamentoRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O Departamento com ID " + idObjeto + " não foi encontrado!"));

        if (departamentoRepository.existsByNome(objeto.getNome())){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possivel cadastrar o Departamento. Já existe outro Departamento com o mesmo nome."));
        }

        LocalDateTime dataHora = paraEditar.getDataCriacao();

        dadosDto.setDataCriacao(dataHora);
        dadosDto.setUltimaAtualizacao(LocalDateTime.now());
        dadosDto.setId(idObjeto);
        dadosDto.setStatus(dadosDto.isStatus());
        BeanUtils.copyProperties(dadosDto, paraEditar, "id");
        Departamento objetoAtualizado = departamentoRepository.saveAndFlush(dadosDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(departamentoMapper.converterParaDto(objetoAtualizado)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {

        Departamento departamento = departamentoRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O Departamento com ID " + idObjeto + " não foi encontrado!"));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(departamentoMapper.converterParaDto(departamento)));
    }

    @Override
    public ResponseEntity<Object> excluir(Long idObjeto) throws Exception {

        departamentoRepository.findById(idObjeto)
                .orElseThrow(()-> new NoSuchElementException("O Departamento com ID " + idObjeto + " não foi encontrado!"));

        departamentoRepository.deleteById(idObjeto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("O Departamento foi excluído com sucesso."));
    }
}
