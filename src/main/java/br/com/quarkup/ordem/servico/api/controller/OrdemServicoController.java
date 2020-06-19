package br.com.quarkup.ordem.servico.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.quarkup.ordem.servico.api.domain.model.OrdemServico;
import br.com.quarkup.ordem.servico.api.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServico;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServico criar(@RequestBody OrdemServico ordemServico) {
        return gestaoOrdemServico.criar(ordemServico);
    }

}
