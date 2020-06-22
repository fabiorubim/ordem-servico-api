package br.com.quarkup.ordem.servico.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.quarkup.ordem.servico.api.domain.model.OrdemServico;
import br.com.quarkup.ordem.servico.api.domain.repository.OrdemServicoRepository;
import br.com.quarkup.ordem.servico.api.domain.service.GestaoOrdemServicoService;
import br.com.quarkup.ordem.servico.api.model.OrdemServicoInputModel;
import br.com.quarkup.ordem.servico.api.model.OrdemServicoModel;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServico;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInput) {
        OrdemServico ordemServico = toEntity(ordemServicoInput);

        return toModel(gestaoOrdemServico.criar(ordemServico));
    }
    // public OrdemServico criar(@Valid @RequestBody OrdemServico ordemServico) {
    // return gestaoOrdemServico.criar(ordemServico);
    // }

    @GetMapping
    public List<OrdemServicoModel> listar() {
        return toCollectionModel(ordemServicoRepository.findAll());
    }
    // public List<OrdemServico> listar() {
    // return ordemServicoRepository.findAll();
    // }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

        if (ordemServico.isPresent()) {
            // Poderia fazer o mapemeamento de campo a campo, por exemplo: (ou utilizar o modelmapper)
            // OrdemServicoRepresentationModel model = new OrdemServicoRepresentationModel();
            // model.setId(ordemServico.get().getId());
            // model.setDescricao(ordemServico.get().getDescricao());

            OrdemServicoModel ordermServicoModel = toModel(ordemServico.get());

            return ResponseEntity.ok(ordermServicoModel);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long ordemServicoId) {
        gestaoOrdemServico.finalizar(ordemServicoId);
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
        return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInputModel) {
        return modelMapper.map(ordemServicoInputModel, OrdemServico.class);
    }

}
