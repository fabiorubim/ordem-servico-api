package br.com.quarkup.ordem.servico.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.quarkup.ordem.servico.api.domain.model.Cliente;
import br.com.quarkup.ordem.servico.api.domain.repository.ClienteRepository;
import br.com.quarkup.ordem.servico.api.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    // @PersistenceContext
    // private EntityManager manager;

    @Autowired
    private ClienteRepository clienteRepository; //O professor diz que colocar tudo dentro da camada Service é opcional, 
    //ele prefere deixar lá somente o que regra de negócio e que irá alterar o BD. Por isso as consultas ficam no Repository. Não existe um padrão.
    
    @Autowired
    private CadastroClienteService cadastroCliente;

    // Utilizando Jakarta Persistence - JPA
    // @GetMapping("/clientes")
    // public List<Cliente> listar() {
    // return manager.createQuery("from Cliente", Cliente.class).getResultList();
    // }

    // Sprintboot JPA
    // @GetMapping("/clientes")
    @GetMapping
    public List<Cliente> listar() {
        // return clienteRepository.findAll();
        // return clienteRepository.findByNome("Fabio Biasi Mello Rubim");
        return clienteRepository.findByNomeContaining("a");
    }

    // @GetMapping("/clientes/{clienteId}")
    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get()); // Retorna 200 e com o cliente
        }

        return ResponseEntity.notFound().build(); // Retorna 404 e nulo
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        //return clienteRepository.save(cliente); //Não irá usar mais o repositório, e sim o Service
        return cadastroCliente.salvar(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId);
        //cliente = clienteRepository.save(cliente); //Não irá usar mais o repositório, e sim o Service
        cadastroCliente.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        //clienteRepository.deleteById(clienteId); //Não irá usar mais o repositório, e sim o Service
        cadastroCliente.exluir(clienteId);
        return ResponseEntity.noContent().build();
    }

}
