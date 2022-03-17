package br.edu.ifpb.domain;

import java.io.Serializable;
import java.util.List;

public interface PessoasInterface extends Serializable {

    public void nova(Pessoa pessoa);

    public List<Pessoa> todas();

    public void excluir(Pessoa pessoa);

    public void atualizar(Pessoa pessoa);

    public List<Pessoa> localizarPessoaComCPF(String cpf);

    public List<Dependente> localizarDependenteComId(Long idPessoa);

}
