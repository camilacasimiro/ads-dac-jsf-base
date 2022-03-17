package br.edu.ifpb.infra.memory;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.Pessoas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PessoasEmMemoria implements Pessoas {


    private final List<Pessoa> pessoas = new ArrayList<>();
    private final List<Dependente> dependentes = new ArrayList<>();

    public PessoasEmMemoria() {
        this.pessoas.addAll(
                Arrays.asList(
                        new Pessoa("Jubileu", "12312312332"),
                        new Pessoa("Cleus", "12312312339"),
                        new Pessoa("Magnus", "12312312330"),
                        new Pessoa("Carlsen", "12312312388"),
                        new Pessoa("Bob Fisher", "12312312337")
                )
        );

        this.dependentes.addAll(
                Arrays.asList(
                        new Dependente(1, "Job", LocalDate.now().plusDays(1)), //amanhã
                        new Dependente(2, "Antonio", null), //nulo
                        new Dependente(3, "Mariana", LocalDate.now().minusDays(1)), //ontem
                        new Dependente(5, "Ana", LocalDate.now()) //hoje
                )
        );
    }


    public void nova(Pessoa pessoa) {
        //TODO: implementar
        this.pessoas.add(pessoa);
    }

    public List<Pessoa> todas() {
        //TODO: implementar
        return Collections.unmodifiableList(pessoas);

    }

    public void excluir(Pessoa pessoa) {
        //TODO: implementar
        this.pessoas.remove(pessoa);
    }

    public void atualizar(Pessoa pessoa) {
        //TODO: implementar
        this.pessoas
                .removeIf(p -> p.getId() == pessoa.getId());
        this.pessoas.add(pessoa);

    }
    @Override
    public Pessoa localizarPessoaComId(long id) {
        return todas() // List<Pessoa>
                .stream() //Stream<Pessoa>
                .filter(p->p.getId() == id) //Stream<Pessoa>
                .findFirst() //Optional<Pessoa>
                .orElse(Pessoa.fake());
    }


    @Override
    public List<Dependente> todosOsDepentendes() {
        return Collections.unmodifiableList(dependentes);
    }

    @Override
    public Dependente localizarDependenteComId(String id) {
        //TODO: implementar
        return todosOsDepentendes()
                .stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(Dependente.fake());
    }

    @Override
    public void novo(Dependente dependente) {
        this.dependentes.add(dependente);
    }
}
