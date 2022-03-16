package br.edu.ifpb.controller;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.DependenteInterface;
import br.edu.ifpb.infra.DependenteJDBC;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

public class DependenteController implements Serializable {

    private DependenteInterface dependenteInterface;
    private Dependente dependente;

    public DependenteController() {
        this.dependenteInterface = (DependenteInterface) new DependenteJDBC();
    }

    public String salvarDependnete(){

        if(this.dependente.getId() > 0){
            this.dependenteInterface.novoDependente(this.dependente);
        } else{
            this.dependenteInterface.atualizarDependente(this.dependente);
        }
        this.dependente = new Dependente();

        return "/Dependente/list?faces-redirect=true";
    }

    public String updateDependente( Dependente dependente){
        this.dependente = dependente;
        return "/Dependente/edit?faces-redirect=true";

    }

    public List<Dependente> listDependentes(){
        List<Dependente> listDependente = this.dependenteInterface.todosDependentes();
        return listDependente;
    }

    public String deleteDependente(Dependente dependente) {
        this.dependenteInterface.excluirDependente(dependente);
        return "/Dependente/list?faces-redirect=true";
    }
}
