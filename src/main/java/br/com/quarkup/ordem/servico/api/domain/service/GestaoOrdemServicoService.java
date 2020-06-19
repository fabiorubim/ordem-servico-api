package br.com.quarkup.ordem.servico.api.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quarkup.ordem.servico.api.domain.model.OrdemServico;
import br.com.quarkup.ordem.servico.api.domain.model.StatusOrdemServico;
import br.com.quarkup.ordem.servico.api.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    public OrdemServico criar(OrdemServico ordemServico) {

        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(LocalDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }
}
