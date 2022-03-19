package br.edu.ifpb.infra;

import br.edu.ifpb.domain.CPF;
import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.PessoasInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PessoaJDBC implements PessoasInterface {

    private static Connection connection;

    private static final Logger logger = Logger.getLogger(PessoaJDBC.class.getName());
    public PessoaJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/atividade01",
                    "jheycami","333"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    public List<Pessoa> todas() {
        try{
            List<Pessoa> pessoas= new ArrayList<>();
            ResultSet resultQuery = connection.prepareStatement( "SELECT * FROM pessoa").executeQuery();
            while ( resultQuery.next() ){
                pessoas.add(converterPessoa(resultQuery));
            }
            return pessoas;

        } catch(SQLException ex){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    public Pessoa converterPessoa (ResultSet result) throws SQLException{
        Long id = result.getLong("id");
        String nome = result.getString("nome");
        CPF cpf = new CPF(result.getString("cpf"));

        return new Pessoa(nome, id, cpf, null);
    }

    @Override
    public void nova(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Pessoa (nome, CPF) VALUES (?, ?)");

            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getCpf().valor());
            statement.executeQuery();

        } catch (SQLException e){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void atualizar(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE pessoa SET nome=? ,CPF=? WHERE id=?");
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getCpf().valor());
            statement.setLong(3, pessoa.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void excluir(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM pessoa WHERE id=?");
            statement.setLong(1, pessoa.getId());
            statement.executeQuery();
        } catch (SQLException e){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public List<Pessoa> localizarPessoaComCPF(String cpf) {
        try{
            List<Pessoa> pessoa= new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM pessoa WHERE CPF = ?");

            statement.setString(1, cpf);
            statement.executeQuery();

            ResultSet pessoasResultSet = statement.getResultSet();

            while ( pessoasResultSet.next() ){
                pessoa.add(converterPessoa(pessoasResultSet));
            }
            return pessoa;

        } catch(SQLException ex){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    public List<Dependente> localizarDependenteComId(Long idPessoa) {
        try{
            List<Dependente> dependentes = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM pessoa_dependente pd INNER JOIN dependente d ON pd.id_dependente = d.id INNER JOIN pessoa p ON pd.id_pessoa = p.id WHERE p.id = ? ");

            statement.setLong(1, idPessoa);
            statement.executeQuery();

            ResultSet dependentesResult = statement.getResultSet();

            while ( dependentesResult.next() ){
                dependentes.add(converterDependentes(dependentesResult));
            }
            return dependentes;

        } catch(SQLException ex){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Dependente converterDependentes (ResultSet result) throws SQLException{
        Integer id = result.getInt("id");
        String nome = result.getString("nome");
        String date = result.getString("dataDeNascimento");
        LocalDate dataDeNascimento = LocalDate.of(
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)),
                Integer.parseInt(date.substring(8, 10))
        );
        return new Dependente(id, nome, dataDeNascimento);
    }
}
