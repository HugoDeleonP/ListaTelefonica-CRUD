package br.com.listatelefonica.dao;

import br.com.listatelefonica.model.Contato;
import br.com.listatelefonica.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    public static void inserirContato(Contato contato) throws SQLException {
        String query = "INSERT INTO contato (nome, telefone, email, observacao) VALUES (?, ?, ?, ?);";

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getObservacao());

            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Contato> listarContatos() throws SQLException{
        String query = "SELECT id, nome, telefone, email, observacao FROM contato;";

        List<Contato> contatos = new ArrayList<>();
        Connection conn = ConnectionFactory.conectar();
        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            int id = rs.getInt("id");
            String nome = rs.getNString("nome");
            String telefone = rs.getString("telefone");
            String email = rs.getString("email");
            String observacao = rs.getString("observacao");

            var contato = new Contato(id, nome, telefone, email, observacao);
            contatos.add(contato);
        }


        return contatos;
    }

    public static List<Contato> listarContatos_nome(String nomeBusca) throws SQLException{
        String query = "SELECT id, nome, telefone, email, observacao FROM contato WHERE nome LIKE ?;";

        List<Contato> contatos = new ArrayList<>();

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, "%" + nomeBusca + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String observacao = rs.getString("observacao");

                var contato = new Contato(id, nome, telefone, email, observacao);
                contatos.add(contato);
            }


        }

        return contatos;

    }

    public static void atualizarContato(Contato contato) throws SQLException{
        String query = """
                UPDATE contato
                SET nome = ?, telefone = ?, email = ?, observacao = ?
                WHERE id LIKE ?;
                """;

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getObservacao());
            stmt.setInt(5, contato.getId());
            stmt.executeUpdate();

            System.out.println("Contato atualizado com sucesso!");
        }
    }
}
