package com.br.Veiculos.service;

import com.br.Veiculos.model.dto.DepartamentoDTO;
import com.br.Veiculos.service.util.CrudService;
import org.springframework.http.ResponseEntity;

public interface DepartamentoService extends CrudService<DepartamentoDTO> {
    ResponseEntity<Object> mudarStatus(Long idObjeto) throws  Exception;
}
