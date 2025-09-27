package br.com.isibank.isibank.controller;

import br.com.isibank.isibank.dto.ContaDTO;
import br.com.isibank.isibank.dto.ResponseDTO;
import br.com.isibank.isibank.service.conta.IContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ContaController {

    @Autowired
    private IContaService service;

    @PostMapping("/contas")
    public ResponseEntity<ResponseDTO> cadastrarConta(@RequestBody ContaDTO conta){
        Integer numConta = service.cadastrarNovaConta(conta);
        if(numConta != null) {
            return ResponseEntity.status(201).body(new ResponseDTO("Conta " + numConta+ " criada com sucesso!"));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("Dados inválodps para conta!"));
    }

}
