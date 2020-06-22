package br.com.quarkup.ordem.servico.api.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quarkup.ordem.servico.api.domain.exception.EntidadeNaoEncontradaException;
import br.com.quarkup.ordem.servico.api.domain.exception.NegocioException;
import br.com.quarkup.ordem.servico.api.domain.model.Cliente;
import br.com.quarkup.ordem.servico.api.domain.model.OrdemServico;
import br.com.quarkup.ordem.servico.api.domain.model.StatusOrdemServico;
import br.com.quarkup.ordem.servico.api.domain.repository.ClienteRepository;
import br.com.quarkup.ordem.servico.api.domain.repository.ComentarioRepository;
import br.com.quarkup.ordem.servico.api.domain.repository.OrdemServicoRepository;
import br.com.quarkup.ordem.servico.api.model.Comentario;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
        
        ordemServico.setCliente(cliente);

        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }
    
    public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        
        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);
        
        return comentarioRepository.save(comentario);
    }

    private OrdemServico buscar(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
    }
    
    public void finalizar(Long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        
        ordemServico.finalizar();
        
        ordemServicoRepository.save(ordemServico);
        
    }
}
