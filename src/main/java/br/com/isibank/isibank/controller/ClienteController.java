package br.com.isibank.isibank.controller;

import br.com.isibank.isibank.dto.ClienteDTO;
import br.com.isibank.isibank.dto.ResponseDTO;
import br.com.isibank.isibank.service.cliente.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @PostMapping("/clientes")
    public ResponseEntity<ResponseDTO> cadastrarNovoCliente(@Valid @RequestBody ClienteDTO cliente) {
        try {
            Integer res = service.cadastrarCliente(cliente);
            if (res != null) {
                return ResponseEntity.status(201).body(new ResponseDTO("Cliente cadastrado com sucesso! " + res));
            }
            return ResponseEntity.badRequest().body(new ResponseDTO("Imposssível cadastrar cliente com dados existentes"));
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Dados do cliente incompletos"));
        }
    }
}
