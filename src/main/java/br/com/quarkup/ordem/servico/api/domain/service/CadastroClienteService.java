package br.com.quarkup.ordem.servico.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quarkup.ordem.servico.api.domain.exception.NegocioException;
import br.com.quarkup.ordem.servico.api.domain.model.Cliente;
import br.com.quarkup.ordem.servico.api.domain.repository.ClienteRepository;

@Service //Indica que é um componente do Spring.
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    public Cliente salvar(Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        
        if (clienteExistente != null && !clienteExistente.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
        }
        
        return clienteRepository.save(cliente);
    }
    
   public void exluir(Long clienteId) {
       clienteRepository.deleteById(clienteId);
   }
}
