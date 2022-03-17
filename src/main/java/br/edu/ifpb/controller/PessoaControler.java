package br.edu.ifpb.controller;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.DependenteInterface;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.PessoasInterface;
import br.edu.ifpb.infra.DependenteJDBC;
import br.edu.ifpb.infra.PessoaJDBC;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("pessoaControler")
@SessionScoped
public class PessoaControler implements Serializable {

    private PessoasInterface pessoasInterface;
    private Pessoa pessoa = new Pessoa();
    private static final Logger logger = Logger.getLogger(PessoaJDBC.class.getName());

    public PessoaControler(){
        this.pessoasInterface = (PessoasInterface) new PessoaJDBC();

    }

    public List<Pessoa> listarPessoas(){
        List<Pessoa> pessoasList = this.pessoasInterface.todas();
        return pessoasList;
    }

    public List<Dependente> listarDependentePorIdPessoa(Long id){
        List<Dependente> dependenteList = pessoasInterface.localizarDependenteComId(id);
        return dependenteList;
    }

    public String gravarPessoa(){
        logger.log(Level.INFO, "Lista depedentepessoa" + this.pessoa.getCpf());
        if(this.pessoa.getId() > 0){
            this.pessoasInterface.atualizar(this.pessoa);
        } else{
            this.pessoasInterface.nova(this.pessoa);
        }
        this.pessoa = new Pessoa();
        return "/pessoa/list?faces-redirect=true";
    }

    public String deletarPessoa(Pessoa pessoa){
        pessoasInterface.excluir(pessoa);
        return "/pessoa/list?faces-redirect=true";
    }

    public String editar(Pessoa pessoa){
        this.pessoa = pessoa;
        return "/pessoa/edit?faces-redirect=true";
    }


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
