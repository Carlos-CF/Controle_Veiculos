package com.br.Veiculos.service;


import com.br.Veiculos.model.dto.MotivoDTO;
import com.br.Veiculos.service.util.CrudService;
import org.springframework.http.ResponseEntity;

public interface MotivoService extends CrudService<MotivoDTO> {

    ResponseEntity<Object> mudarStatus(Long idObjeto) throws  Exception;
}
