package dev.piccodev.daarquiteturaaodeploy.repository;

import dev.piccodev.daarquiteturaaodeploy.domain.Transacao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {

    //SELECT * FROM transacao ORDER BY nome_loja ASC, id DESC;
    List<Transacao> findAllByOrderByNomeDaLojaAscIdDesc();
}
