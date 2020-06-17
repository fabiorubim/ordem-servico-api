package br.com.quarkup.ordem.servico.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.quarkup.ordem.servico.api.domain.model.Cliente;

@RestController
public class ClienteController {

    @GetMapping("/clientes")
    public List<Cliente> listar() {
        var cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Fabio Biasi");
        cliente1.setTelefone("15-99142-0289");
        cliente1.setEmail("fabiorubim@hotmail.com");
        
        
        var cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("João");
        cliente2.setTelefone("15-96547-9852");
        cliente2.setEmail("joao@hotmail.com");

        return Arrays.asList(cliente1, cliente2);
    }
}
