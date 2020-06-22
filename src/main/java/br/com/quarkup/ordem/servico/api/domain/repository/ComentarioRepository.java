package br.com.quarkup.ordem.servico.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.quarkup.ordem.servico.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
