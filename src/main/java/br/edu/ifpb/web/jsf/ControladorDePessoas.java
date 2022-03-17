package br.edu.ifpb.web.jsf;

import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.Pessoas;
import br.edu.ifpb.domain.PessoasInterface;
import br.edu.ifpb.infra.memory.PessoasEmMemoria;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("controle")
@SessionScoped
public class ControladorDePessoas implements Serializable {

    private Pessoas pessoas = new PessoasEmMemoria();
    private Pessoa pessoa = new Pessoa( "");

    public String adicionar(){

        Pessoa pessoaLocalizada = this.pessoas.localizarPessoaComId(
                this.pessoa.getId()
        );

        if(Pessoa.fake().equals(pessoaLocalizada)){
            this.pessoas.nova(this.pessoa);
        }else{
            this.pessoas.atualizar(this.pessoa);
        }

        this.pessoa = new Pessoa("");
        return "list?faces-redirect=true";
    }

//    public String exibir(){
//        return "list?faces-redirect=true";
//    }

    public String remover(Pessoa pessoa){

        this.pessoas.excluir(pessoa);
        return null;
    }

    public String editar(Pessoa pessoa){
        this.pessoa = pessoa;
        return "edit?faces-redirect=true";
    }

    public List<Pessoa> todasAsPessoas(){

        return this.pessoas.todas();
    }

    public Pessoa getPessoa() {

        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {

        this.pessoa = pessoa;
    }

}
