package com.br.Veiculos.service;

import com.br.Veiculos.model.dto.TransportadoraDTO;
import com.br.Veiculos.service.util.CrudService;
import org.springframework.http.ResponseEntity;

public interface TransportadoraService extends CrudService<TransportadoraDTO> {

    ResponseEntity<Object> mudarStatus(Long idObjeto) throws  Exception;
}
