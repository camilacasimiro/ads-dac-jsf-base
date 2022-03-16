package br.edu.ifpb.infra;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.DependenteInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DependenteJDBC implements DependenteInterface {

    private static Connection connection;

    public DependenteJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/atividade01",
                    "jheycami","333"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DependenteJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    public void novoDependente(Dependente dependente) {
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO dependente (nome, dataDeNascimento) VALUES (?, ?)");

            statement.setString(1, dependente.getNome());
            statement.setDate(2, java.sql.Date.valueOf(dependente.getDataDeNascimento()));
            statement.executeQuery();

        } catch (SQLException e){
            Logger.getLogger(DependenteJDBC.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public List<Dependente> todosDependentes() {
        try{
            List<Dependente> dependentes= new ArrayList<>();
            ResultSet resultQuery = connection.prepareStatement( "SELECT * FROM dependente").executeQuery();

            while ( resultQuery.next() ){
                dependentes.add(converterDependentes(resultQuery));
                System.out.println(dependentes);
            }
            return dependentes;

        } catch(SQLException ex){
            Logger.getLogger(DependenteJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    public Dependente converterDependentes (ResultSet result) throws SQLException{
        int id = result.getInt("id");
        String nome = result.getString("nome");
        String date = result.getString("dataDeNascimento");
        LocalDate dataDeNascimento = LocalDate.of(
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)),
                Integer.parseInt(date.substring(8, 10))
        );

        return new Dependente(id, nome, dataDeNascimento);
    }

    @Override
    public void excluirDependente(Dependente dependente) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM dependente WHERE id=?");
            statement.setInt(1, dependente.getId());
            statement.executeQuery();
        } catch (SQLException e){
            Logger.getLogger(DependenteJDBC.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public void atualizarDependente(Dependente dependente) {
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE dependente SET nome=?, dataDeNascimento=? WHERE id=?");
            statement.setString(1, dependente.getNome());
            statement.setString(2, String.valueOf(dependente.getDataDeNascimento()));
            statement.setInt(3, dependente.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(DependenteJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
