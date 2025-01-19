package br.ufrn.eaj.tads.rafael.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.eaj.tads.rafael.model.Especialidade;
import br.ufrn.eaj.tads.rafael.util.DatabaseUtil;

public class EspecialidadeDAO {

    // Criar uma nova especialidade
    public void create(Especialidade especialidade) throws SQLException {
        String sql = "INSERT INTO especialidade (nome) VALUES (?)";
        try {
        	Connection connection = DatabaseUtil.getConnection();
        	PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            		
            stmt.setString(1, especialidade.getNome());
            stmt.executeUpdate();

            // Recupera o ID gerado
            
        	ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                especialidade.setId(rs.getInt(1));
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ler uma especialidade pelo ID
    public Especialidade readById(int id) {
        String sql = "SELECT id, nome FROM especialidade WHERE id = ?";
        try {
        	Connection connection = DatabaseUtil.getConnection();
        	PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Especialidade(
                        rs.getInt("id"),
                        rs.getString("nome")
                    );
                }
            }
             
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return null;
        
    }

    // Atualizar uma especialidade
    public void update(Especialidade especialidade) throws SQLException {
        String sql = "UPDATE especialidade SET nome = ? WHERE id = ?";
        try {
        	Connection connection = DatabaseUtil.getConnection();
        	PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, especialidade.getNome());
            stmt.setInt(2, especialidade.getId());
            stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    // Deletar uma especialidade
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM especialidade WHERE id = ?";
        try {
        	Connection connection = DatabaseUtil.getConnection();
        	PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
        
    }

    // Listar todas as especialidades
    public List<Especialidade> listAll() {
        String sql = "SELECT id, nome FROM especialidade";
        List<Especialidade> especialidades = new ArrayList<>();

        try {
        	Connection connection = DatabaseUtil.getConnection();
        	PreparedStatement stmt = connection.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Especialidade especialidade = new Especialidade(
                    rs.getInt("id"),
                    rs.getString("nome")
                );
                especialidades.add(especialidade);
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return especialidades;
        
    }

}
