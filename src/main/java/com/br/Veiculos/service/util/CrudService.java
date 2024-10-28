package com.br.Veiculos.service.util;

import org.springframework.http.ResponseEntity;

public interface CrudService<T> {

    ResponseEntity<Object> cadastrar(T objeto) throws Exception;

    ResponseEntity<Object> listar() throws Exception;

    ResponseEntity<Object> editar(Long idObjeto, T objeto) throws Exception;

    ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception;

    ResponseEntity<Object> excluir(Long idObjeto) throws Exception;
}