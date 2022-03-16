package br.edu.ifpb.domain;

import java.io.Serializable;
import java.util.List;

public interface DependenteInterface extends Serializable {

    public void novoDependente(Dependente dependente);

    public List<Dependente> todosDependentes();

    public void excluirDependente(Dependente dependente);

    public void atualizarDependente(Dependente dependente);

}
