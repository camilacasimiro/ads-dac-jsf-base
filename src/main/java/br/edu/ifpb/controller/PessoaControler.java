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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("pessoaControler")
@SessionScoped
public class PessoaControler implements Serializable {

    private PessoasInterface pessoasInterface;
    private Pessoa pessoa = new Pessoa();
    private Dependente dependenteSelect = new Dependente();
    private List<Pessoa> pessoaList = new ArrayList<>();
    private String cpf = "";
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
        logger.log(Level.INFO, "dependente selec" + this.dependenteSelect);
        if(Objects.isNull(this.pessoa.getId())){
            this.pessoasInterface.nova(this.pessoa);
        } else{
            this.pessoasInterface.atualizar(this.pessoa);
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

    public List<Pessoa> buscaCpf() {
        this.pessoaList = this.pessoasInterface.localizarPessoaComCPF(cpf);
        return pessoaList;
    }


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Dependente getDependenteSelect() {
        return dependenteSelect;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDependenteSelect(Dependente dependenteSelect) {
        this.dependenteSelect = dependenteSelect;
    }
}
