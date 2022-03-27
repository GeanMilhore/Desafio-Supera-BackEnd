package br.com.banco.controller.conta;

import br.com.banco.controller.conta.contaDto.ContaRequest;
import br.com.banco.controller.conta.contaDto.ContaResponse;
import br.com.banco.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class ContaController {

    public final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponse> autenticar(@RequestBody ContaRequest request){
        return ResponseEntity.ok(contaService.autenticar(request));
    }
}
