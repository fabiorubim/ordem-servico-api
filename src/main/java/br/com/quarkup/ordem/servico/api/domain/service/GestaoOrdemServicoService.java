package br.com.quarkup.ordem.servico.api.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quarkup.ordem.servico.api.domain.exception.NegocioException;
import br.com.quarkup.ordem.servico.api.domain.model.Cliente;
import br.com.quarkup.ordem.servico.api.domain.model.OrdemServico;
import br.com.quarkup.ordem.servico.api.domain.model.StatusOrdemServico;
import br.com.quarkup.ordem.servico.api.domain.repository.ClienteRepository;
import br.com.quarkup.ordem.servico.api.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        
        ordemServico.setCliente(cliente);

        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }
}
