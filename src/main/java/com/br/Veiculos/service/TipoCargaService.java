package com.br.Veiculos.service;

import com.br.Veiculos.model.dto.TipoCargaDTO;
import com.br.Veiculos.service.util.CrudService;
import org.springframework.http.ResponseEntity;

public interface TipoCargaService extends CrudService<TipoCargaDTO> {

    ResponseEntity<Object> mudarStatus(Long idObjeto) throws  Exception;
}
