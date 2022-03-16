package br.edu.ifpb.infra;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.PessoasInterface;
import com.pratica.controller.BandaController;
import com.pratica.domain.Banda;
import com.pratica.domain.BandaInterface;

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
    public List<Pessoa> todas() throws ClassNotFoundException, SQLException {
        try{
            List<Pessoa> pessoas= new ArrayList<>();
            ResultSet resultQuery = connection.prepareStatement( "SELECT * FROM pessoa").executeQuery();
//            next percore o ResultSet e reforna false quando estar na ultima posição
            while ( resultQuery.next() ){
                pessoas.add(converterPessoa(resultQuery));
                System.out.println(pessoas);
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
        String cpf = result.getString("cpf");

        return new Pessoa(nome, id, cpf, null);
    }

    @Override
    public void nova(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Pessoa (nome, cpf) VALUES (?, ?)");

            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getCpf().toString());
            statement.executeQuery();

        } catch (SQLException e){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void atualizar(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE banda SET nome=?,cpf=? WHERE id=?");
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getCpf().toString());
            statement.setLong(3, pessoa.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void excluir(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM banda WHERE id=?");
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
                    "SELECT * FROM pessoa WHERE cpf = ?");

            statement.setString(1, cpf);
            statement.executeQuery();

            ResultSet bandaResult = statement.getResultSet();

            while ( bandaResult.next() ){
                pessoa.add(converterPessoa(bandaResult));
            }
            return pessoa;

        } catch(SQLException ex){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    public List<Dependente> localizarDependenteComId(Long idPessoa) {
        try{

            logger.log(Level.INFO, "Banda busca Entrando ");
            List<Dependente> dependentes = null;

            PreparedStatement statement = connection.prepareStatement(
                    "DISTINCT SELECT * FROM pessoa_dependente WHERE id_pessoal= ?");

            statement.setLong(1, idPessoa);
            statement.executeQuery();

            ResultSet dependentesResult = statement.getResultSet();

                dependentes = converterDependente(dependentesResult);

                logger.log(Level.INFO, "Banda busca : " + dependentes);

            return dependentes;

        } catch(SQLException ex){
            Logger.getLogger(PessoaJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Dependente converterDependente (ResultSet result) throws SQLException{
        Long id = result.getLong("id");
        String nome = result.getString("nome");
        String date = result.getString("dataDeNascimento");
        LocalDate dataDeNascimento = LocalDate.of(
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)),
                Integer.parseInt(date.substring(8, 10))
        );
        return new Pessoa(id, nome, dataDeNascimento);
    }
}
