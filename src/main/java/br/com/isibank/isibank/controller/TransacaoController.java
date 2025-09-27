package br.com.isibank.isibank.controller;

import br.com.isibank.isibank.dto.PagamentoDTO;
import br.com.isibank.isibank.dto.ResponseDTO;
import br.com.isibank.isibank.service.transacao.ITransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TransacaoController {

    @Autowired
    private ITransacaoService service;

    @PostMapping("/pagamentos")
    public ResponseEntity<ResponseDTO> efetuarPagamento(@RequestBody PagamentoDTO pagamento){
        try{
            if(service.efetuarPagamento(pagamento)){
                return ResponseEntity.ok(new ResponseDTO("Pagamento efetuado!"));
            }
            return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possível realizar operação!"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ResponseDTO(ex.getMessage()));
        }
    }
}
