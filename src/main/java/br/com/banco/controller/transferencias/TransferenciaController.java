package br.com.banco.controller.transferencias;

import br.com.banco.controller.transferencias.saldoDto.SaldoResponse;
import br.com.banco.model.Transferencia;
import br.com.banco.repository.filter.TransferenciaFilter;
import br.com.banco.service.TransferenciaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/transferencias")
public class TransferenciaController {

    private TransferenciaService transferenciaService;

    @GetMapping()
    public ResponseEntity<Page<Transferencia>> buscar(@PageableDefault(size = 4) Pageable pageable, TransferenciaFilter filter){
        return ResponseEntity.ok(transferenciaService.buscarTransferencias(pageable, filter));
    }

    @GetMapping("/saldos")
    public ResponseEntity<SaldoResponse> buscarSaldos(TransferenciaFilter filter){
        return ResponseEntity.ok(transferenciaService.buscarSaldos(filter));
    }

}
