package br.edu.ifpb.controller;

import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.PessoasInterface;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("pessoaControler")
@SessionScoped
public class PessoaControler implements Serializable {

    private PessoasInterface pessoasInterface;
    private Pessoa pessoa = new Pessoa();

    public PessoaControler(){

    }

    public List<Pessoa> listarPessoas(){
        List<Pessoa> pessoasList = pessoasInterface.todas();
        return pessoasList;
    }

    public String gravarPessoa(){
        if(this.pessoa.getId() > 0){
            this.pessoasInterface.atualizar(this.pessoa);
        } else{
            this.pessoasInterface.atualizar(this.pessoa);
        }
        this.pessoa = new Pessoa();
        return "/Pessoa/list?faces-redirect=true";
    }

    public void deletarPessoa(Pessoa pessoa){
        pessoasInterface.excluir(pessoa);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
